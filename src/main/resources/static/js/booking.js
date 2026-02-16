const BASE_URL = "http://localhost:8080/api/v1/bookings";
let bookingList = [];

$(document).ready(function () {
    loadBookings();
});

// LOAD ALL BOOKINGS
function loadBookings() {
    $.get(`${BASE_URL}/getAll`, function (response) {
        const data = response.content ? response.content : response;
        bookingList = Array.isArray(data) ? data : [];

        let rows = "";
        if (bookingList.length === 0) {
            rows = "<tr><td colspan='6'>No bookings found.</td></tr>";
        } else {
            bookingList.forEach((b, idx) => {
                rows += `
                <tr>
                    <td>${b.id}</td>
                    <td>${b.bookingDate}</td>
                    <td>${b.location}</td>
                    <td><span class="badge ${getStatusBadge(b.status)}">${b.status}</span></td>
                    <td>${b.clientEmail}</td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="editBooking(${idx})"><i class="fa fa-edit"></i></button>
                        <button class="btn btn-sm btn-danger" onclick="deleteBooking(${b.id})"><i class="fa fa-trash"></i></button>
                    </td>
                </tr>`;
            });
        }
        $("#bookingTableBody").html(rows);
    }).fail(() => {
        $("#bookingTableBody").html("<tr><td colspan='6' class='text-danger'>Error connecting to server.</td></tr>");
    });
}

// FORM SUBMIT
$("#bookingForm").submit(function (e) {
    e.preventDefault();
    const id = $("#bookingId").val();
    const booking = {
        bookingDate: $("#bookingDate").val(),
        location: $("#location").val(),
        status: $("#status").val(),
        clientEmail: $("#clientEmail").val()
    };
    if (id) {
        booking.id = id;
        updateBooking(booking);
    } else {
        saveBooking(booking);
    }
});

// SAVE
function saveBooking(booking) {
    $.ajax({
        url: `${BASE_URL}/save`,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(booking),
        success: () => { alert("Booking Saved!"); loadBookings(); resetForm(); },
        error: (xhr) => { alert("Save failed: " + (xhr.responseJSON?.message || "Server error")); }
    });
}

// UPDATE
function updateBooking(booking) {
    $.ajax({
        url: `${BASE_URL}/update`,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(booking),
        success: () => { alert("Booking Updated!"); loadBookings(); resetForm(); },
        error: () => { alert("Update failed."); }
    });
}

// DELETE
function deleteBooking(id) {
    if (!confirm(`Are you sure you want to delete/cancel booking #${id}?`)) return;
    $.ajax({
        url: `${BASE_URL}/delete/${id}`,
        method: "DELETE",
        success: () => { alert("Booking deleted."); loadBookings(); },
        error: () => { alert("Delete failed."); }
    });
}

// EDIT
function editBooking(idx) {
    const b = bookingList[idx];
    $("#bookingId").val(b.id);
    $("#bookingDate").val(b.bookingDate);
    $("#location").val(b.location);
    $("#status").val(b.status);
    $("#clientEmail").val(b.clientEmail);
    $("#formTitle").text(`Update Booking #${b.id}`);
    window.scrollTo(0, 0);
}

// RESET FORM
function resetForm() {
    $("#bookingForm")[0].reset();
    $("#bookingId").val("");
    $("#formTitle").text("Add New Booking");
}

// STATUS BADGE HELPER
function getStatusBadge(status) {
    if (status === 'CONFIRMED') return 'badge-success';
    if (status === 'CANCELLED') return 'badge-danger';
    return 'badge-secondary';
}