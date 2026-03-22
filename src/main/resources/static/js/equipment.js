const BASE_URL = "http://localhost:8080/api/v1/equipment";
const TOKEN_KEY = "jwt_token";

$(document).ready(() => {
    fetchEquipment();
    $("#equipmentForm").on("submit", handleFormSubmit);
});

// Fetch All
async function fetchEquipment() {
    const token = localStorage.getItem(TOKEN_KEY);
    try {
        const res = await fetch(`${BASE_URL}/getAll`, {
            headers: { "Authorization": `Bearer ${token}` }
        });
        const data = await res.json();
        const tbody = $("#equipmentTableBody");
        tbody.empty();

        const items = data.content || [];
        if (items.length > 0) {
            $("#noData").addClass("d-none");
            items.forEach(e => {
                tbody.append(`
                    <tr>
                        <td class="ps-4">#${e.id}</td>
                        <td class="fw-bold">${e.itemName}</td>
                        <td><code>${e.serialNumber}</code></td>
                        <td><span class="badge ${e.status === 'AVAILABLE' ? 'bg-success' : 'bg-warning'}">${e.status}</span></td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-primary me-1" onclick="loadToForm(${e.id}, '${e.itemName}', '${e.serialNumber}', '${e.status}')">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteEquipment(${e.id})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>`);
            });
        } else {
            $("#noData").removeClass("d-none");
        }
    } catch (err) { console.error(err); }
}

// Populate Form for Edit
function loadToForm(id, name, sn, status) {
    $("#equipmentId").val(id); // Set hidden field
    $("#itemName").val(name);
    $("#serialNumber").val(sn);
    $("#status").val(status);

    $("#saveBtn").html('<i class="fas fa-sync me-1"></i> Update').removeClass("btn-success").addClass("btn-primary");
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Save or Update
async function handleFormSubmit(e) {
    e.preventDefault();
    const token = localStorage.getItem(TOKEN_KEY);
    const idValue = $("#equipmentId").val();

    const dto = {
        itemName: $("#itemName").val(),
        serialNumber: $("#serialNumber").val(),
        status: $("#status").val()
    };

    const isUpdate = (idValue !== "" && idValue !== null);
    if (isUpdate) dto.id = parseInt(idValue); // Convert to Number for Java Long

    const endpoint = isUpdate ? `${BASE_URL}/update` : `${BASE_URL}/save`;
    const method = isUpdate ? "PUT" : "POST";

    try {
        const res = await fetch(endpoint, {
            method: method,
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(dto)
        });

        const result = await res.json();
        if (result.code === "00") {
            alert("✅ Success!");
            resetForm();
            fetchEquipment();
        } else {
            alert("⚠️ " + result.message);
        }
    } catch (err) { alert("❌ Error connecting to server."); }
}

function resetForm() {
    $("#equipmentId").val("");
    $("#equipmentForm")[0].reset();
    $("#saveBtn").html('<i class="fas fa-save me-1"></i> Save').removeClass("btn-primary").addClass("btn-success");
}

async function deleteEquipment(id) {
    if (!confirm("Are you sure?")) return;
    const token = localStorage.getItem(TOKEN_KEY);
    await fetch(`${BASE_URL}/delete/${id}`, {
        method: 'DELETE',
        headers: { "Authorization": `Bearer ${token}` }
    });
    fetchEquipment();
}