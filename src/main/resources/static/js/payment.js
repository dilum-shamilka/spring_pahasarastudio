const apiBase = "http://localhost:8080/api/v1/payment";

// 1. Unified Auth Headers
function getAuthHeaders() {
    const token = localStorage.getItem("jwt_token") || localStorage.getItem("token") || localStorage.getItem("jwtToken");
    return {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
    };
}

// 2. JWT Expiration Check
function isTokenExpired(token) {
    if (!token || token === "undefined") return true;
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.exp * 1000 < Date.now();
    } catch (e) { return true; }
}

function handleAuthError() {
    console.error("Authentication failed or session expired.");
    localStorage.removeItem("jwt_token");
    window.location.href = "index.html";
}

// 3. Load Payments Table
function loadPayments() {
    const token = localStorage.getItem("jwt_token") || localStorage.getItem("token");
    if (isTokenExpired(token)) return handleAuthError();

    $.ajax({
        url: `${apiBase}/getAll`,
        method: "GET",
        headers: getAuthHeaders(),
        success: function(res) {
            const tbody = $("#paymentTable tbody").empty();
            const payments = res.content || res;

            if (payments && payments.length > 0) {
                $("#noData").addClass("d-none");
                payments.forEach(p => {
                    // Logic to color code payment methods
                    const methodBadge = p.paymentMethod === 'Cash' ? 'bg-success' : 'bg-info';

                    tbody.append(`
                        <tr>
                            <td class="ps-4 fw-bold">#${p.id}</td>
                            <td><span class="badge bg-light text-dark border">Inv #${p.invoiceId}</span></td>
                            <td class="fw-bold text-success">Rs. ${parseFloat(p.amount).toLocaleString(undefined, {minimumFractionDigits: 2})}</td>
                            <td><span class="badge ${methodBadge}">${p.paymentMethod}</span></td>
                            <td class="text-muted small">${new Date(p.transactionTime).toLocaleString()}</td>
                            <td class="text-center">
                                <button class="btn btn-sm btn-outline-primary edit-btn" 
                                    data-id="${p.id}" data-invoice="${p.invoiceId}" 
                                    data-amount="${p.amount}" data-method="${p.paymentMethod}" 
                                    data-time="${p.transactionTime}">Edit</button>
                                <button class="btn btn-sm btn-outline-danger delete-btn" data-id="${p.id}">Delete</button>
                            </td>
                        </tr>`);
                });
            } else {
                $("#noData").removeClass("d-none");
            }
        },
        error: function(xhr) {
            if(xhr.status === 401 || xhr.status === 403) handleAuthError();
        }
    });
}

// 4. Save / Update
$("#paymentForm").submit(function(e) {
    e.preventDefault();
    const id = $("#paymentId").val();

    // Ensure we are sending valid numbers and ISO dates
    const data = {
        id: id ? parseInt(id) : null,
        invoiceId: parseInt($("#invoiceId").val()),
        amount: parseFloat($("#amount").val()),
        paymentMethod: $("#paymentMethod").val(),
        transactionTime: new Date($("#transactionTime").val()).toISOString()
    };

    $.ajax({
        url: id ? `${apiBase}/update` : `${apiBase}/save`,
        method: id ? "PUT" : "POST",
        headers: getAuthHeaders(),
        data: JSON.stringify(data),
        success: function() {
            alert(id ? "Payment Updated!" : "Payment Recorded!");
            loadPayments();
            resetForm();
        },
        error: (err) => alert("Error: " + (err.responseJSON?.message || "Check fields"))
    });
});

// 5. Delete Logic (Added)
$(document).on("click", ".delete-btn", function() {
    const id = $(this).data("id");
    if(confirm("Are you sure you want to remove this payment record?")) {
        $.ajax({
            url: `${apiBase}/delete/${id}`,
            method: "DELETE",
            headers: getAuthHeaders(),
            success: function() {
                loadPayments();
            },
            error: () => alert("Delete failed. This payment may be locked.")
        });
    }
});

// 6. Edit Mapping
$(document).on("click", ".edit-btn", function() {
    const d = $(this).data();
    $("#paymentId").val(d.id);
    $("#invoiceId").val(d.invoice);
    $("#amount").val(d.amount);
    $("#paymentMethod").val(d.method);

    // Format date for datetime-local input (YYYY-MM-DDTHH:MM)
    if(d.time) {
        const localDate = new Date(d.time);
        localDate.setMinutes(localDate.getMinutes() - localDate.getTimezoneOffset());
        $("#transactionTime").val(localDate.toISOString().slice(0,16));
    }

    $("#formHeader").text("Update Payment #" + d.id);
    window.scrollTo({ top: 0, behavior: 'smooth' });
});

function resetForm() {
    $("#paymentForm")[0].reset();
    $("#paymentId").val("");
    $("#formHeader").text("Add / Update Payment");
}

$(document).ready(() => loadPayments());