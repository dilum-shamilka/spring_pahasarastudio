// client.js
const BASE_URL = "http://localhost:8080/api/v1/clients";

$(document).ready(function() {
    getAllClients();

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
        }
    });
}

function deleteClient() {
    const id = $("#clientId").val();
    if (!id) return alert("Select a client first");

    $.ajax({
        url: BASE_URL + "/delete/" + id,
        method: "DELETE",
        success: function () {
            alert("Deleted Successfully");
            getAllClients();
            resetForm();
        }
    });
}

function getAllClients() {
    $.ajax({
        url: BASE_URL + "/getAll",
        method: "GET",
        success: function (response) {
            let clients = response.content;
            let rows = "";

            if (clients) {
                clients.forEach(c => {
                    rows += `<tr onclick="setFormData(${c.id},'${c.fullName}','${c.email}','${c.phoneNumber}','${c.address ?? ''}')">
                      <td>${c.id}</td>
                      <td>${c.fullName}</td>
                      <td>${c.email}</td>
                      <td>${c.phoneNumber}</td>
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
}

function resetForm() {
    $("#clientId").val("");
    $(".form-control").val("");
}

function getFormData() {
    return {
        fullName: $("#fullName").val(),
        email: $("#email").val(),
        phoneNumber: $("#phoneNumber").val(),
        address: $("#address").val()
    };
}
