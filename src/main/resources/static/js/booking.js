const BOOKING_API = "http://localhost:8080/api/v1/bookings";
const SERVICE_API = "http://localhost:8080/api/v1/services";

let bookingList = [];
let serviceList = [];

$(document).ready(function () {
    loadServices().then(() => loadBookings());

    $("#resetBtn").click(resetForm);

    $("#bookingForm").submit(function (e) {
        e.preventDefault();
        saveBooking();
    });
});

// Load services first
async function loadServices() {
    try {
        const response = await $.get(`${SERVICE_API}/getAll`);
        serviceList = response || [];
        let options = '<option value="" selected disabled>Select a Package</option>';
        serviceList.forEach(s => {
            if (s.status === 'ACTIVE') {
                options += `<option value="${s.id}">${s.serviceName} (Rs. ${s.price})</option>`;
            }
        });
        $("#serviceId").html(options);
    } catch (err) {
        console.error("Failed to load services:", err);
    }
}

// Load bookings into table
function loadBookings() {
    $.ajax({
        url: `${BOOKING_API}/getAll`,
        method: "GET",
        success: function (response) {
            bookingList = response || [];
            let rows = "";

            if (!bookingList.length) {
                $("#noData").removeClass("d-none");
                $("#bookingTableBody").html("");
                return;
            } else {
                $("#noData").addClass("d-none");
            }

            bookingList.forEach(b => {
                let statusClass = "badge-status";
                if (b.status === "CONFIRMED") statusClass += " status-confirmed";
                else if (b.status === "CANCELLED") statusClass += " status-cancelled";
                else statusClass += " status-pending";

                const serviceOption = serviceList.find(s => s.id == b.serviceId);
                const serviceText = serviceOption ? serviceOption.serviceName : '<span class="text-danger small">Not Selected</span>';

                rows += `
                <tr>
                    <td>#${b.id}</td>
                    <td>${b.bookingDate}</td>
                    <td>${serviceText}</td>
                    <td>${b.location}</td>
                    <td><span class="${statusClass}">${b.status}</span></td>
                    <td>${b.clientEmail}</td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-outline-primary me-1" onclick="editBooking(${b.id})">Edit</button>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteBooking(${b.id})">Delete</button>
                    </td>
                </tr>`;
            });

            $("#bookingTableBody").html(rows);
        },
        error: function (err) {
            console.error("Failed to load bookings:", err);
        }
    });
}

// Save or update
function saveBooking() {
    const id = $("#bookingId").val();
    const dto = {
        id: id ? parseInt(id) : null,
        bookingDate: $("#bookingDate").val(),
        location: $("#location").val(),
        status: $("#status").val(),
        clientEmail: $("#clientEmail").val(),
        serviceId: $("#serviceId").val()
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${BOOKING_API}/update` : `${BOOKING_API}/save`;

    $.ajax({
        url: url,
        method: method,
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: function (res) {
            if (res.status === "success") {
                alert(res.message);
                resetForm();
                loadBookings();
            } else {
                alert("Error: " + res.message);
            }
        },
        error: function (err) {
            console.error("Failed to save booking:", err);
            alert("Failed to save booking. Check console.");
        }
    });
}

// Edit
function editBooking(id) {
    const b = bookingList.find(x => x.id === id);
    if (!b) return;

    $("#bookingId").val(b.id);
    $("#bookingDate").val(b.bookingDate);
    $("#location").val(b.location);
    $("#status").val(b.status);
    $("#clientEmail").val(b.clientEmail);
    $("#serviceId").val(b.serviceId);

    $("#saveBtn").text("Update").removeClass("btn-success").addClass("btn-primary");
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Delete
function deleteBooking(id) {
    if (!confirm("Are you sure to delete?")) return;

    $.ajax({
        url: `${BOOKING_API}/delete/${id}`,
        method: "DELETE",
        success: function (res) {
            if (res.status === "success") {
                alert(res.message);
                loadBookings();
            } else alert("Failed to delete booking.");
        },
        error: function (err) {
            console.error("Failed to delete booking:", err);
            alert("Failed to delete booking.");
        }
    });
}

// Reset form
function resetForm() {
    $("#bookingForm")[0].reset();
    $("#bookingId").val("");
    $("#saveBtn").text("Save").removeClass("btn-primary").addClass("btn-success");
}
