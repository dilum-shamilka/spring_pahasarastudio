const BASE_URL = "http://localhost:8080/api/v1/bookings";
let bookingList = [];

$(document).ready(function () {
    loadBookings();

    $("#resetBtn").click(function() {
        resetForm();
    });
});

// LOAD DATA
function loadBookings() {
    $.get(`${BASE_URL}/getAll`, function (response) {
        const data = response.content ? response.content : response;
        bookingList = Array.isArray(data) ? data : [];

        let rows = "";
        if (bookingList.length === 0) {
            $("#noData").removeClass("d-none");
            $("#bookingTableBody").html("");
        } else {
            $("#noData").addClass("d-none");
            bookingList.forEach((b) => {
                rows += `
                <tr>
                    <td class="text-muted">#${b.id}</td>
                    <td>${b.bookingDate}</td>
                    <td>${b.location}</td>
                    <td><span class="badge-status">${b.status}</span></td>
                    <td>${b.clientEmail}</td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-outline-primary me-1" onclick="editBooking(${b.id})">Edit</button>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteBooking(${b.id})">Delete</button>
                    </td>
                </tr>`;
            });
            $("#bookingTableBody").html(rows);
        }
    }).fail(() => {
        console.error("Backend connection failed.");
    });
}

// SAVE & UPDATE
$("#bookingForm").submit(function (e) {
    e.preventDefault();

    const id = $("#bookingId").val();
    const dto = {
        id: id ? parseInt(id) : null,
        bookingDate: $("#bookingDate").val(),
        location: $("#location").val(),
        status: $("#status").val(),
        clientEmail: $("#clientEmail").val()
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${BASE_URL}/update` : `${BASE_URL}/save`;

    $.ajax({
        url: url,
        method: method,
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: (res) => {
            if (res.code === "00" || res.status === "success" || !res.code) {
                alert(res.message || "Booking saved successfully!");
                resetForm();
                loadBookings();
            } else {
                alert("Error: " + res.message);
            }
        },
        error: (xhr) => {
            alert("Execution failed: " + (xhr.responseJSON?.message || "Check backend connection."));
        }
    });
});

// EDIT FUNCTION
function editBooking(id) {
    const b = bookingList.find(x => x.id === id);
    if (b) {
        $("#bookingId").val(b.id);
        $("#bookingDate").val(b.bookingDate);
        $("#location").val(b.location);
        $("#status").val(b.status);
        $("#clientEmail").val(b.clientEmail);

        // UI Change to match User Management style
        $("#saveBtn").text("Update").removeClass("btn-success").addClass("btn-primary");
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }
}

// DELETE FUNCTION
function deleteBooking(id) {
    if (confirm("Permanently delete this booking?")) {
        $.ajax({
            url: `${BASE_URL}/delete/${id}`,
            method: "DELETE",
            success: (res) => {
                alert(res.message || "Deleted");
                loadBookings();
            }
        });
    }
}

function resetForm() {
    $("#bookingForm")[0].reset();
    $("#bookingId").val("");
    $("#saveBtn").text("Save").removeClass("btn-primary").addClass("btn-success");
}