const BASE_URL = "http://localhost:8080/api/v1/invoices";



// Load data on start
document.addEventListener("DOMContentLoaded", getAllInvoices);

// 1. GET ALL
function getAllInvoices() {
    fetch(`${BASE_URL}/getAll`)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('invoiceTableBody');
            tbody.innerHTML = '';

            if (data.code === "00" && data.content) {
                data.content.forEach(inv => {
                    // Determine badge class
                    let statusClass = '';
                    if (inv.paymentStatus === 'PAID') statusClass = 'bg-paid';
                    else if (inv.paymentStatus === 'UNPAID') statusClass = 'bg-unpaid';
                    else statusClass = 'bg-warning text-dark'; // ADVANCED

                    tbody.innerHTML += `
                        <tr>
                            <td class="ps-4 fw-bold">${inv.id}</td>  <!-- Invoice ID -->
                            <td>${inv.invoiceNumber}</td>           <!-- Invoice Number -->
                            <td>${inv.bookingId}</td>
                            <td>${inv.date}</td>
                            <td>Rs. ${parseFloat(inv.totalAmount).toLocaleString()}</td>
                            <td><span class="status-badge ${statusClass}">${inv.paymentStatus}</span></td>
                            <td class="text-center">
                                <button class="btn btn-sm btn-outline-primary me-1" onclick="prepareUpdate(${inv.id})">Edit</button>
                                <button class="btn btn-sm btn-outline-danger" onclick="deleteInvoice(${inv.id})">Delete</button>
                            </td>
                        </tr>`;
                });
            } else {
                tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted py-3">No invoices found.</td></tr>`;
            }
        })
        .catch(err => console.error("Error loading table:", err));
}

// 2. SAVE (Triggered by Form Submit)
document.getElementById('invoiceForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // If btnUpdate is visible, we should be updating, not saving
    if (!document.getElementById('btnUpdate').classList.contains('d-none')) return;

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
                alert("Saved!");
                resetUI();
                getAllInvoices();
            } else {
                alert("Error: " + data.message);
            }
        });
});

// 3. PREPARE UPDATE (Fills the form)
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
            }
        });
}

// 4. UPDATE (Triggered by Update Button)
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
                alert("Updated!");
                resetUI();
                getAllInvoices();
            }
        });
}

// 5. DELETE
function deleteInvoice(id) {
    if(confirm("Confirm Delete?")) {
        fetch(`${BASE_URL}/delete/${id}`, { method: 'DELETE' })
            .then(res => res.json())
            .then(() => { getAllInvoices(); });
    }
}

// Reset form UI
function resetUI() {
    document.getElementById('invoiceForm').reset();
    document.getElementById('btnSave').classList.remove('d-none');
    document.getElementById('btnUpdate').classList.add('d-none');
    document.getElementById('formHeader').innerText = "Generate New Invoice";


}

