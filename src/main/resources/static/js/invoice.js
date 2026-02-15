const BASE_URL = "http://localhost:8080/api/v1/invoices";

document.addEventListener("DOMContentLoaded", getAllInvoices);

// --- 1. GET ALL INVOICES ---
function getAllInvoices() {
    fetch(`${BASE_URL}/getAll`)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('invoiceTableBody');
            tbody.innerHTML = '';

            if (data.code === "00" && data.content) {
                data.content.forEach(inv => {
                    const statusClass = inv.paymentStatus === 'PAID' ? 'bg-paid' : 'bg-unpaid';
                    tbody.innerHTML += `
                        <tr>
                            <td class="ps-4 fw-bold">${inv.invoiceNumber}</td>
                            <td># ${inv.bookingId}</td>
                            <td>${inv.date}</td>
                            <td>Rs. ${inv.totalAmount.toLocaleString()}</td>
                            <td><span class="status-badge ${statusClass}">${inv.paymentStatus}</span></td>
                            <td class="text-center">
                                <button class="btn btn-sm btn-outline-primary me-1" onclick="prepareUpdate(${inv.id})">Edit</button>
                                <button class="btn btn-sm btn-outline-danger" onclick="deleteInvoice(${inv.id})">Delete</button>
                            </td>
                        </tr>
                    `;
                });
            } else {
                tbody.innerHTML = '<tr><td colspan="6" class="text-center py-4">No records found</td></tr>';
            }
        });
}

// --- 2. SAVE INVOICE ---
document.getElementById('invoiceForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const dto = {
        invoiceNumber: document.getElementById('invoiceNumber').value,
        bookingId: parseInt(document.getElementById('bookingId').value),
        date: document.getElementById('invoiceDate').value,
        totalAmount: parseFloat(document.getElementById('totalAmount').value),
        paymentStatus: document.getElementById('paymentStatus').value
    };

    fetch(`${BASE_URL}/save`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    })
        .then(res => res.json())
        .then(data => {
            if(data.code === "00") {
                alert("Success: Invoice Generated");
                resetUI();
                getAllInvoices();
            } else {
                alert("Error: " + data.message);
            }
        });
});

// --- 3. PREPARE UPDATE ---
function prepareUpdate(id) {
    fetch(`${BASE_URL}/get/${id}`)
        .then(res => res.json())
        .then(data => {
            if (data.code === "00") {
                const inv = data.content;
                document.getElementById('invoiceId').value = inv.id;
                document.getElementById('invoiceNumber').value = inv.invoiceNumber;
                document.getElementById('bookingId').value = inv.bookingId;
                document.getElementById('invoiceDate').value = inv.date;
                document.getElementById('totalAmount').value = inv.totalAmount;
                document.getElementById('paymentStatus').value = inv.paymentStatus;

                document.getElementById('btnSave').classList.add('d-none');
                document.getElementById('btnUpdate').classList.remove('d-none');
                document.getElementById('formHeader').innerText = "Update Invoice: " + inv.invoiceNumber;
            } else {
                alert("Invoice not found");
            }
        });
}

// --- 4. UPDATE INVOICE ---
function updateInvoice() {
    const dto = {
        id: parseInt(document.getElementById('invoiceId').value),
        invoiceNumber: document.getElementById('invoiceNumber').value,
        bookingId: parseInt(document.getElementById('bookingId').value),
        date: document.getElementById('invoiceDate').value,
        totalAmount: parseFloat(document.getElementById('totalAmount').value),
        paymentStatus: document.getElementById('paymentStatus').value
    };

    fetch(`${BASE_URL}/update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    })
        .then(res => res.json())
        .then(data => {
            if(data.code === "00") {
                alert("Update Successful");
                resetUI();
                getAllInvoices();
            } else {
                alert("Failed: " + data.message);
            }
        });
}

// --- 5. DELETE INVOICE ---
function deleteInvoice(id) {
    if(confirm("Permanently delete this invoice?")) {
        fetch(`${BASE_URL}/delete/${id}`, { method: 'DELETE' })
            .then(res => res.json())
            .then(data => {
                alert(data.message);
                getAllInvoices();
            });
    }
}

// --- 6. RESET UI ---
function resetUI() {
    document.getElementById('invoiceForm').reset();
    document.getElementById('invoiceId').value = "";
    document.getElementById('btnSave').classList.remove('d-none');
    document.getElementById('btnUpdate').classList.add('d-none');
    document.getElementById('formHeader').innerText = "Generate New Invoice";
}
