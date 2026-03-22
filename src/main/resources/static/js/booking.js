const BOOKING_API = "http://localhost:8080/api/v1/bookings";
const SERVICE_API = "http://localhost:8080/api/v1/services";

let bookingList = [];
let serviceList = [];

// 1. Global AJAX Configuration for JWT
$.ajaxSetup({
    beforeSend: function(xhr) {
        const token = localStorage.getItem("jwt_token");
        if (token) {
            xhr.setRequestHeader('Authorization', `Bearer ${token}`);
        }
    },
    error: function(jqXHR) {
        if (jqXHR.status === 401 || jqXHR.status === 403) {
            alert("Session expired. Please login again.");
            window.location.href = "index.html";
        }
    }
});

$(document).ready(function () {
    if (!localStorage.getItem("jwt_token")) {
        window.location.href = "index.html";
        return;
    }

    // Sequence: Load services FIRST, then bookings
    initPage();

    $("#resetBtn").click(resetForm);
    $("#bookingForm").submit(function (e) {
        e.preventDefault();
        saveBooking();
    });
});

async function initPage() {
    await loadServices(); // Wait for services to finish
    loadBookings();       // Now load bookings so the names can be mapped
}

// ------------------ LOAD SERVICES ------------------
async function loadServices() {
    try {
        const response = await $.get(`${SERVICE_API}/getAll`);
        // Handle both response formats (wrapped in 'content' or direct array)
        serviceList = response.content || response || [];

        let options = '<option value="" selected disabled>Select a Package</option>';
        serviceList.forEach(s => {
            if (s.status === 'ACTIVE') {
                options += `<option value="${s.id}">${s.serviceName} (Rs. ${s.price})</option>`;
            }
        });
        $("#serviceId").html(options);
        console.log("Services loaded:", serviceList.length);
    } catch (err) {
        console.error("Failed to load services:", err);
    }
}

// ------------------ LOAD BOOKINGS ------------------
function loadBookings() {
    $.ajax({
        url: `${BOOKING_API}/getAll`,
        method: "GET",
        success: function (response) {
            bookingList = response.content || response || [];
            let rows = "";

            if (!bookingList || bookingList.length === 0) {
                $("#noData").removeClass("d-none");
                $("#bookingTableBody").html("");
                return;
            } else {
                $("#noData").addClass("d-none");
            }

            bookingList.forEach(b => {
                let statusClass = "badge bg-opacity-10 ";
                if (b.status === "CONFIRMED") statusClass += "bg-success text-success";
                else if (b.status === "CANCELLED") statusClass += "bg-danger text-danger";
                else statusClass += "bg-warning text-warning";

                // CRITICAL FIX: Find service name from the loaded serviceList
                const service = serviceList.find(s => String(s.id) === String(b.serviceId));
                const serviceName = service ? service.serviceName : `<span class="text-danger">ID: ${b.serviceId}</span>`;

                rows += `
                <tr>
                    <td>#${b.id}</td>
                    <td>${b.bookingDate}</td>
                    <td class="fw-bold text-primary">${serviceName}</td>
                    <td>${b.location}</td>
                    <td><span class="${statusClass} px-2 py-1 rounded small">${b.status}</span></td>
                    <td>${b.clientEmail}</td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-outline-primary me-1" onclick="editBooking(${b.id})"><i class="fas fa-edit"></i></button>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteBooking(${b.id})"><i class="fas fa-trash"></i></button>
                    </td>
                </tr>`;
            });

            $("#bookingTableBody").html(rows);
        }
    });
}

// ------------------ SAVE/UPDATE ------------------
function saveBooking() {
    const id = $("#bookingId").val();
    const dto = {
        id: id ? parseInt(id) : null,
        bookingDate: $("#bookingDate").val(),
        location: $("#location").val(),
        status: $("#status").val(),
        clientEmail: $("#clientEmail").val(),
        serviceId: $("#serviceId").val() // Selected from dropdown
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${BOOKING_API}/update` : `${BOOKING_API}/save`;

    $.ajax({
        url: url,
        method: method,
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: function (res) {
            if (res.code === "00" || res.status === 200) {
                alert("Success!");
                resetForm();
                loadBookings();
            } else {
                alert("Error: " + res.message);
            }
        },
        error: function(xhr) {
            alert("Failed: " + (xhr.responseJSON?.message || "Check client email exists"));
        }
    });
}

// ------------------ DELETE/EDIT/RESET ------------------
function deleteBooking(id) {
    if (!confirm("Delete this booking?")) return;
    $.ajax({
        url: `${BOOKING_API}/delete/${id}`,
        method: "DELETE",
        success: function () {
            alert("Deleted");
            loadBookings();
        }
    });
}

function editBooking(id) {
    const b = bookingList.find(x => x.id === id);
    if (!b) return;

    $("#bookingId").val(b.id);
    $("#bookingDate").val(b.bookingDate);
    $("#location").val(b.location);
    $("#status").val(b.status);
    $("#clientEmail").val(b.clientEmail);
    $("#serviceId").val(b.serviceId); // Dropdown will auto-select the matching ID

    $("#saveBtn").text("Update").removeClass("btn-success").addClass("btn-primary");
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function resetForm() {
    $("#bookingForm")[0].reset();
    $("#bookingId").val("");
    $("#saveBtn").text("Save").removeClass("btn-primary").addClass("btn-success");
}