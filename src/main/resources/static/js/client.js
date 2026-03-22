const BASE_URL = "http://localhost:8080/api/v1/clients";
const TOKEN_KEY = "jwt_token"; // Matches Dashboard

$(document).ready(function () {
    const token = localStorage.getItem(TOKEN_KEY);

    // 1. Initial Auth Check
    if (!token || token === "undefined") {
        window.location.href = "index.html";
        return;
    }

    // 2. Global AJAX Setup
    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        error: function (xhr) {
            if (xhr.status === 401 || xhr.status === 403) {
                alert("Session expired. Please log in again.");
                localStorage.clear();
                window.location.href = "index.html";
            }
        }
    });

    // 3. Page Initialization
    getAllClients();

    // 4. Event Listeners
    $("#saveBtn").click(saveClient);
    $("#updateBtn").click(updateClient);
    $("#resetBtn").click(resetForm);
});

// ------------------ READ ------------------
function getAllClients() {
    $.ajax({
        url: `${BASE_URL}/getAll`,
        method: "GET",
        success: function (response) {
            // Spring handles data in .content if using Pageable, otherwise raw array
            const clients = response.content || response;
            let rows = "";

            if (clients && clients.length > 0) {
                $("#noData").addClass("d-none");
                clients.forEach(c => {
                    rows += `
                    <tr>
                        <td class="ps-4 text-muted">#${c.id}</td>
                        <td>${c.name}</td>
                        <td>${c.email}</td>
                        <td><span class="badge bg-info-subtle text-info border border-info-subtle px-2 py-1">${c.contactNumber}</span></td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-primary me-1" onclick="loadClient(${c.id}, '${escapeQuotes(c.name)}', '${escapeQuotes(c.email)}', '${escapeQuotes(c.contactNumber)}', '${escapeQuotes(c.address)}')">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteClient(${c.id})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>`;
                });
            } else {
                $("#noData").removeClass("d-none");
            }
            $("#clientTableBody").html(rows);
        },
        error: function(xhr) {
            console.error("Fetch Error:", xhr);
            $("#noData").removeClass("d-none").text("Server connection failed.");
        }
    });
}

// ------------------ CREATE ------------------
function saveClient() {
    const data = getFormData();
    if (!data.name || !data.email) return alert("Name and Email are required!");

    $.ajax({
        url: `${BASE_URL}/save`,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function () {
            alert("Client saved successfully!");
            getAllClients();
            resetForm();
        },
        error: function(xhr) {
            alert("Error: " + (xhr.responseText || "Could not save client."));
        }
    });
}

// ------------------ UPDATE ------------------
function updateClient() {
    const data = getFormData();
    data.id = $("#clientId").val();

    $.ajax({
        url: `${BASE_URL}/update`,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function () {
            alert("Client updated successfully!");
            getAllClients();
            resetForm();
        },
        error: function(xhr) {
            alert("Error: " + (xhr.responseText || "Could not update client."));
        }
    });
}

// ------------------ DELETE ------------------
function deleteClient(id) {
    if (confirm(`Are you sure you want to delete client #${id}?`)) {
        $.ajax({
            url: `${BASE_URL}/delete/${id}`,
            method: "DELETE",
            success: function () {
                alert("Client deleted.");
                getAllClients();
            },
            error: function(xhr) {
                alert("Delete failed: " + xhr.responseText);
            }
        });
    }
}

// ------------------ HELPERS ------------------

function loadClient(id, name, email, phone, address) {
    $("#clientId").val(id);
    $("#fullName").val(name);
    $("#email").val(email);
    $("#phoneNumber").val(phone);
    $("#address").val(address);

    // Toggle buttons
    $("#saveBtn").addClass("d-none");
    $("#updateBtn").removeClass("d-none");
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function resetForm() {
    $("#clientId").val("");
    $("#fullName, #email, #phoneNumber, #address").val("");
    $("#saveBtn").removeClass("d-none");
    $("#updateBtn").addClass("d-none");
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
    if (!str) return "";
    return str.replace(/'/g, "\\'").replace(/"/g, "&quot;");
}