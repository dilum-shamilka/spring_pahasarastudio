const BASE_URL = "http://localhost:8080/api/v1/clients";

$(document).ready(function () {

    getAllClients();

    $("#updateBtn, #deleteBtn").prop("disabled", true);

    $("#saveBtn").click(saveClient);
    $("#updateBtn").click(updateClient);
    $("#deleteBtn").click(deleteClient);
    $("#resetBtn").click(resetForm);
});

function saveClient() {

    const client = getFormData();

    $.ajax({
        url: BASE_URL + "/save",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(client),
        success: function (res) {
            alert(res.message);
            getAllClients();
            resetForm();
        },
        error: function (err) {
            alert(err.responseJSON?.message || "Error saving client");
        }
    });
}

function updateClient() {

    const client = getFormData();
    client.id = $("#clientId").val();

    $.ajax({
        url: BASE_URL + "/update",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(client),
        success: function (res) {
            alert(res.message);
            getAllClients();
            resetForm();
        },
        error: function (err) {
            alert(err.responseJSON?.message || "Error updating client");
        }
    });
}

function deleteClient() {

    const id = $("#clientId").val();

    if (!id) {
        alert("Select a client first");
        return;
    }

    $.ajax({
        url: BASE_URL + "/delete/" + id,
        method: "DELETE",
        success: function (res) {
            alert(res.message);
            getAllClients();
            resetForm();
        },
        error: function () {
            alert("Error deleting client");
        }
    });
}

function getAllClients() {

    $.ajax({
        url: BASE_URL + "/getAll",
        method: "GET",
        success: function (response) {

            const clients = response.content;
            let rows = "";

            if (clients) {

                clients.forEach(c => {

                    rows += `
                    <tr onclick="setFormData(${c.id}, '${escapeQuotes(c.name)}', '${escapeQuotes(c.email)}', '${escapeQuotes(c.contactNumber)}', '${escapeQuotes(c.address)}')">
                        <td>${c.id}</td>
                        <td>${c.name}</td>
                        <td>${c.email}</td>
                        <td>${c.contactNumber}</td>
                        <td>${c.address ?? ''}</td>
                    </tr>`;
                });
            }

            $("#clientTableBody").html(rows);
        }
    });
}

function setFormData(id, name, email, phone, address) {

    $("#clientId").val(id);
    $("#fullName").val(name);
    $("#email").val(email);
    $("#phoneNumber").val(phone);
    $("#address").val(address);

    $("#updateBtn, #deleteBtn").prop("disabled", false);
}

function resetForm() {

    $("#clientId").val("");
    $(".form-control").val("");

    $("#updateBtn, #deleteBtn").prop("disabled", true);
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