const baseUrl = "http://localhost:8080/api/v1/inventory";

document.addEventListener("DOMContentLoaded", loadItems);

function loadItems() {
    fetch(baseUrl + "/getAll")
        .then(res => res.json())
        .then(res => {
            const tableBody = document.getElementById("inventoryTable");
            const noData = document.getElementById("noData");
            tableBody.innerHTML = "";

            if (res.content && res.content.length > 0) {
                noData.classList.add("d-none");
                res.content.forEach(item => {
                    // Logic for Status Badges
                    let statusHtml = '';
                    if (item.quantity <= 0) {
                        statusHtml = '<span class="status-badge bg-out">Out of Stock</span>';
                    } else if (item.quantity <= item.reorderLevel) {
                        statusHtml = '<span class="status-badge bg-low">Low Stock</span>';
                    } else {
                        statusHtml = '<span class="status-badge bg-available">Available</span>';
                    }

                    let row = `
                    <tr id="row-${item.id}">
                        <td class="fw-bold text-muted">#${item.id}</td>
                        <td class="fw-semibold">${item.itemName}</td>
                        <td>${item.quantity}</td>
                        <td>${item.unit}</td>
                        <td>${statusHtml}</td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-primary me-1" 
                                onclick="selectItem(${item.id}, '${item.itemName}', ${item.quantity}, '${item.unit}', ${item.reorderLevel})">
                                Edit
                            </button>
                            <button class="btn btn-sm btn-outline-danger" 
                                onclick="deleteItem(${item.id})">
                                Delete
                            </button>
                        </td>
                    </tr>`;
                    tableBody.innerHTML += row;
                });
            } else {
                noData.classList.remove("d-none");
            }
        }).catch(err => console.error("Error loading items:", err));
}

function saveItem() {
    const data = getFormData();
    if (!data.itemName) return alert("Item Name is required.");

    fetch(baseUrl + "/save", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(res => {
            alert("Item saved successfully!");
            loadItems();
            clearForm();
        });
}

function updateItem() {
    const id = document.getElementById("itemId").value;
    const data = getFormData();
    data.id = id;

    fetch(baseUrl + "/update", {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(res => {
            alert("Item updated!");
            loadItems();
            clearForm();
        });
}

function deleteItem(id) {
    if(confirm("Permanently delete this item?")) {
        fetch(baseUrl + "/delete/" + id, { method: "DELETE" })
            .then(res => res.json())
            .then(res => {
                alert("Item deleted.");
                loadItems();
                clearForm();
            });
    }
}

function selectItem(id, name, qty, unit, reorder) {
    document.getElementById("itemId").value = id;
    document.getElementById("itemName").value = name;
    document.getElementById("quantity").value = qty;
    document.getElementById("unit").value = unit;
    document.getElementById("reorderLevel").value = reorder;

    document.getElementById("formHeader").innerText = "Editing: " + name;

    // Toggle Buttons
    document.getElementById("saveBtn").classList.add("d-none");
    document.getElementById("updateBtn").classList.remove("d-none");

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function clearForm() {
    document.getElementById("inventoryForm").reset();
    document.getElementById("itemId").value = "";
    document.getElementById("formHeader").innerText = "Item Details";

    document.getElementById("saveBtn").classList.remove("d-none");
    document.getElementById("updateBtn").classList.add("d-none");
}

function getFormData() {
    return {
        itemName: document.getElementById("itemName").value,
        quantity: parseInt(document.getElementById("quantity").value) || 0,
        unit: document.getElementById("unit").value,
        reorderLevel: parseInt(document.getElementById("reorderLevel").value) || 0
    };
}