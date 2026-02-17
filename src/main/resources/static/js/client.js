const BASE_URL = "http://localhost:8080/api/v1/clients";

$(document).ready(function () {
    getAllClients();

    $("#saveBtn").click(saveClient);
    $("#updateBtn").click(updateClient);
    $("#resetBtn").click(resetForm);
});

function getAllClients() {
    $.ajax({
        url: BASE_URL + "/getAll",
        method: "GET",
        success: function (response) {
            const clients = response.content;
            let rows = "";
            const noData = $("#noData");

            if (clients && clients.length > 0) {
                noData.addClass("d-none");
                clients.forEach(c => {
                    rows += `
                    <tr>
                        <td class="text-muted font-weight-bold">#${c.id}</td>
                        <td>${c.name}</td>
                        <td>${c.email}</td>
                        <td><span class="badge-contact">${c.contactNumber}</span></td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-primary me-1" onclick="loadClient(${c.id}, '${escapeQuotes(c.name)}', '${escapeQuotes(c.email)}', '${escapeQuotes(c.contactNumber)}', '${escapeQuotes(c.address)}')">Edit</button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteClient(${c.id})">Delete</button>
                        </td>
                    </tr>`;
                });
            } else {
                noData.removeClass("d-none");
            }
            $("#clientTableBody").html(rows);
        },
        error: function() {
            $("#noData").removeClass("d-none").text("Error connecting to server.");
        }
    });
}

function loadClient(id, name, email, phone, address) {
    $("#clientId").val(id);
    $("#fullName").val(name);
    $("#email").val(email);
    $("#phoneNumber").val(phone);
    $("#address").val(address);

    // Button Swap Logic
    $("#saveBtn").addClass("d-none");
    $("#updateBtn").removeClass("d-none");

    // Smooth scroll to form
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function updateClient() {
    const client = getFormData();
    client.id = $("#clientId").val();

    if (!client.id) {
        alert("Selection error.");
        return;
    }

    $.ajax({
        url: BASE_URL + "/update",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(client),
        success: function (res) {
            alert("Client updated successfully!");
            getAllClients();
            resetForm();
        },
        error: function(err) {
            alert("Update failed: " + (err.responseJSON?.message || "Unknown error"));
        }
    });
}

function resetForm() {
    $("#clientId").val("");
    $("#fullName, #email, #phoneNumber, #address").val("");
    $("#saveBtn").removeClass("d-none");
    $("#updateBtn").addClass("d-none");
}

function saveClient() {
    const client = getFormData();
    $.ajax({
        url: BASE_URL + "/save",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(client),
        success: function () {
            alert("Client saved!");
            getAllClients();
            resetForm();
        }
    });
}

function deleteClient(id) {
    if(confirm("Permanently delete client #" + id + "?")) {
        $.ajax({
            url: BASE_URL + "/delete/" + id,
            method: "DELETE",
            success: function () {
                getAllClients();
                resetForm();
            }
        });
    }
}

function getFormData() {
    return {
        name: $("#fullName").val(),
        email: $("#email").val(),
        contactNumber: $("#phoneNumber").val(),
        address: $("#address").val()
    };
}

function escapeQuotes(str) {
    return str ? str.replace(/'/g, "\\'") : "";
}