const baseUrl = "http://localhost:8080/api/v1/inventory";

document.addEventListener("DOMContentLoaded", loadItems);

function loadItems() {
    fetch(baseUrl + "/getAll")
        .then(res => res.json())
        .then(res => {
            const tableBody = document.getElementById("inventoryTable");
            tableBody.innerHTML = "";

            if (res.content && Array.isArray(res.content)) {
                res.content.forEach(item => {
                    const isLow = item.quantity <= item.reorderLevel;
                    const statusBadge = isLow
                        ? `<span class="status-badge bg-unpaid">Low Stock</span>`
                        : `<span class="status-badge bg-paid">In Stock</span>`;

                    let row = `
<tr id="row-${item.id}" class="tr-clickable" onclick="selectItem(${item.id}, '${item.itemName}', ${item.quantity}, '${item.unit}', ${item.reorderLevel})">
  <td class="ps-4 fw-bold text-muted">#${item.id}</td>
  <td class="fw-semibold">${item.itemName}</td>
  <td>${item.quantity}</td>
  <td>${item.unit}</td>
  <td>${item.reorderLevel}</td>
  <td class="text-center">${statusBadge}</td>
</tr>`;
                    tableBody.innerHTML += row;
                });
            } else {
                tableBody.innerHTML = "<tr><td colspan='6' class='text-center py-4 text-muted'>No records found</td></tr>";
            }
        }).catch(err => console.error("Error loading items:", err));
}

function saveItem() {
    const data = {
        itemName: document.getElementById("itemName").value,
        quantity: parseInt(document.getElementById("quantity").value),
        unit: document.getElementById("unit").value,
        reorderLevel: parseInt(document.getElementById("reorderLevel").value)
    };

    fetch(baseUrl + "/save", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(res => {
            alert(res.message);
            loadItems();
            clearForm();
        });
}

function updateItem() {
    const id = document.getElementById("itemId").value;
    if (!id) return alert("Please select an item first.");

    const data = {
        id: id,
        itemName: document.getElementById("itemName").value,
        quantity: parseInt(document.getElementById("quantity").value),
        unit: document.getElementById("unit").value,
        reorderLevel: parseInt(document.getElementById("reorderLevel").value)
    };

    fetch(baseUrl + "/update", {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(res => {
            alert(res.message);
            loadItems();
            clearForm();
        });
}

function deleteItem() {
    const id = document.getElementById("itemId").value;
    if (!id) return alert("Please select an item to delete.");

    if(confirm("Permanently delete this item?")) {
        fetch(baseUrl + "/delete/" + id, { method: "DELETE" })
            .then(res => res.json())
            .then(res => {
                alert(res.message);
                loadItems();
                clearForm();
            });
    }
}

function selectItem(id, name, qty, unit, reorder) {
    document.querySelectorAll('tr').forEach(tr => tr.classList.remove('selected-row'));
    const targetRow = document.getElementById(`row-${id}`);
    if(targetRow) targetRow.classList.add('selected-row');

    document.getElementById("itemId").value = id;
    document.getElementById("itemName").value = name;
    document.getElementById("quantity").value = qty;
    document.getElementById("unit").value = unit;
    document.getElementById("reorderLevel").value = reorder;
    document.getElementById("formHeader").innerText = "Editing: " + name;
}

function clearForm() {
    document.querySelectorAll('tr').forEach(tr => tr.classList.remove('selected-row'));
    document.getElementById("itemId").value = "";
    document.getElementById("itemName").value = "";
    document.getElementById("quantity").value = "";
    document.getElementById("unit").value = "";
    document.getElementById("reorderLevel").value = "";
    document.getElementById("formHeader").innerText = "Item Details";
}