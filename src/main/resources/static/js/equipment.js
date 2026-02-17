const BASE_URL = "http://localhost:8080/api/v1/equipment";

$(document).ready(() => {
    fetchEquipment();
    $("#resetBtn").click(resetForm);
});

// ================= FETCH ALL =================
async function fetchEquipment() {
    try {
        const res = await fetch(`${BASE_URL}/getAll`);
        const data = await res.json();
        const tbody = $("#equipmentTableBody");
        const noData = $("#noData");
        tbody.empty();

        // Check for '00' success code or standard array
        if (data.content && data.content.length > 0) {
            noData.addClass("d-none");
            data.content.forEach(e => {
                tbody.append(`
                    <tr id="row-${e.id}">
                        <td>#${e.id}</td>
                        <td class="fw-bold">${e.itemName}</td>
                        <td>${e.serialNumber}</td>
                        <td><span class="status-badge">${e.status}</span></td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-primary me-1" onclick="editEquipment(${e.id})">Edit</button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteEquipment(${e.id})">Delete</button>
                        </td>
                    </tr>`);
            });
        } else {
            noData.removeClass("d-none");
        }
    } catch (err) {
        console.error("Server error:", err);
    }
}

// ================= SAVE / UPDATE =================
document.getElementById("equipmentForm").addEventListener("submit", async e => {
    e.preventDefault();

    const id = $("#equipmentId").val();
    const dto = {
        id: id ? parseInt(id) : null,
        itemName: $("#itemName").val().trim(),
        serialNumber: $("#serialNumber").val().trim(),
        status: $("#status").val(),
        equipmentImages: []
    };

    const endpoint = id ? `${BASE_URL}/update` : `${BASE_URL}/save`;
    const method = id ? "PUT" : "POST";

    try {
        const res = await fetch(endpoint, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dto)
        });

        const result = await res.json();

        if (res.ok || result.code === "00") {
            alert(result.message || "Operation Successful");
            resetForm();
            fetchEquipment();
        } else {
            alert("Error: " + result.message);
        }
    } catch (err) {
        alert("Connection error.");
    }
});

// ================= EDIT (FIXED) =================
async function editEquipment(id) {
    try {
        // Double-check if your backend uses /get/ID or /search/ID
        const res = await fetch(`${BASE_URL}/get/${id}`);
        const data = await res.json();

        // Check both '00' code and standard success response
        if (data.code === "00" || data.content) {
            const e = data.content;

            // Populate Form Fields
            $("#equipmentId").val(e.id);
            $("#itemName").val(e.itemName);
            $("#serialNumber").val(e.serialNumber);
            $("#status").val(e.status);

            // Change Save Button to Update Mode
            $("#saveBtn").text("Update")
                .removeClass("btn-success")
                .addClass("btn-primary");

            // Smooth scroll to form
            window.scrollTo({ top: 0, behavior: 'smooth' });
        } else {
            alert("Equipment details not found.");
        }
    } catch (err) {
        console.error("Edit error:", err);
        alert("Error loading item details.");
    }
}

// ================= DELETE =================
async function deleteEquipment(id) {
    if (confirm("Permanently delete this equipment?")) {
        try {
            const res = await fetch(`${BASE_URL}/delete/${id}`, { method: "DELETE" });
            const result = await res.json();

            if (res.ok || result.code === "00") {
                alert(result.message || "Deleted Successfully");
                fetchEquipment();
            } else {
                alert("Delete failed: " + result.message);
            }
        } catch (err) {
            alert("Delete failed due to connection error.");
        }
    }
}

// ================= RESET =================
function resetForm() {
    $("#equipmentId").val(""); // Explicitly clear hidden ID
    $("#equipmentForm")[0].reset(); // Reset visible fields

    // Revert Button UI
    $("#saveBtn").text("Save")
        .removeClass("btn-primary")
        .addClass("btn-success");
}