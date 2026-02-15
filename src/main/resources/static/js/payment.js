// payment.js
const apiBase = "http://localhost:8080/api/v1/payment";

// Load all payments
function loadPayments() {
    $.ajax({
        url: `${apiBase}/getAll`,
        method: "GET",
        success: function(res) {
            const tbody = $("#paymentTable tbody");
            tbody.empty();

            const payments = res.content;
            if (payments && Array.isArray(payments) && payments.length > 0) {
                payments.forEach(p => {
                    const time = p.transactionTime ? new Date(p.transactionTime).toLocaleString() : '-';
                    const methodClass = `method-${p.paymentMethod.toLowerCase()}`;

                    const tr = `<tr>
                        <td class="pl-4 font-weight-bold">#${p.id}</td>
                        <td>Inv #${p.invoiceId}</td>
                        <td>Rs. ${p.amount.toLocaleString(undefined, {minimumFractionDigits: 2})}</td>
                        <td><span class="status-badge ${methodClass}">${p.paymentMethod}</span></td>
                        <td class="text-muted small">${time}</td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-info edit-btn" data-id="${p.id}">Edit</button>
                            <button class="btn btn-sm btn-outline-danger delete-btn" data-id="${p.id}">Delete</button>
                        </td>
                    </tr>`;
                    tbody.append(tr);
                });
            } else {
                tbody.append('<tr><td colspan="6" class="text-center py-4">No payment records found</td></tr>');
            }
        },
        error: function(err) {
            console.error(err);
            alert("Failed to load payments.");
        }
    });
}

// Save or update payment
$("#paymentForm").submit(function(e) {
    e.preventDefault();
    const paymentData = {
        id: $("#paymentId").val() || null,
        invoiceId: parseInt($("#invoiceId").val()),
        amount: parseFloat($("#amount").val()),
        paymentMethod: $("#paymentMethod").val(),
        transactionTime: $("#transactionTime").val() ? new Date($("#transactionTime").val()).toISOString() : null
    };

    const url = paymentData.id ? `${apiBase}/update` : `${apiBase}/save`;
    const method = paymentData.id ? "PUT" : "POST";

    $.ajax({
        url: url,
        method: method,
        contentType: "application/json",
        data: JSON.stringify(paymentData),
        success: function(res) {
            alert(res.message);
            loadPayments();
            $("#resetBtn").click();
        },
        error: function(err) {
            alert("Error: " + (err.responseJSON ? err.responseJSON.message : "Unknown error"));
        }
    });
});

// Reset form
$("#resetBtn").click(function() {
    $("#paymentForm")[0].reset();
    $("#paymentId").val("");
    $("#saveBtn").text("Save Record").removeClass("btn-primary").addClass("btn-success");
    $("#formHeader").text("Add / Update Payment");
});

// Edit payment
$(document).on("click", ".edit-btn", function() {
    const id = $(this).data("id");
    $.ajax({
        url: `${apiBase}/getAll`,
        method: "GET",
        success: function(res) {
            const payment = res.content.find(p => p.id === id);
            if (payment) {
                $("#paymentId").val(payment.id);
                $("#invoiceId").val(payment.invoiceId);
                $("#amount").val(payment.amount);
                $("#paymentMethod").val(payment.paymentMethod);
                if (payment.transactionTime) {
                    $("#transactionTime").val(new Date(payment.transactionTime).toISOString().slice(0,16));
                }
                $("#saveBtn").text("Update Now").removeClass("btn-success").addClass("btn-primary");
                $("#formHeader").text("Updating Payment #" + payment.id);
            }
        }
    });
});

// Delete payment
$(document).on("click", ".delete-btn", function() {
    const id = $(this).data("id");
    if(confirm("Permanently delete this payment record?")) {
        $.ajax({
            url: `${apiBase}/delete/${id}`,
            method: "DELETE",
            success: function(res) {
                alert(res.message);
                loadPayments();
            }
        });
    }
});

// Initial load
$(document).ready(function() {
    loadPayments();
});
