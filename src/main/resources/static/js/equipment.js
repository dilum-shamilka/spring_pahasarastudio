const BASE_URL = "http://localhost:8080/api/v1/equipment";

$(document).ready(() => {
    getAllEquipment();

    $("#btnSave").click(saveEquipment);
    $("#btnUpdate").click(updateEquipment);
    $("#btnDelete").click(deleteEquipment);
    $("#btnClear").click(resetForm);
});

// ================= GET ALL EQUIPMENT =================
function getAllEquipment() {
    $.get(`${BASE_URL}/getAll`, (response) => {
        const tableBody = $("#equipmentTableBody");
        tableBody.empty();

        const equipment = response.content || [];

        if (equipment.length > 0) {
            equipment.forEach(e => {
                const badgeClass = e.status === 'AVAILABLE' ? 'badge-available' :
                    e.status === 'REPAIRING' ? 'badge-repairing' : 'badge-retired';

                const row = $(`
                    <tr id="row-${e.id}" class="tr-clickable" 
                        onclick="selectEquipment(${e.id}, '${e.itemName}', '${e.serialNumber}', '${e.status}')">
                        <td class="ps-4 fw-bold text-muted">#${e.id}</td>
                        <td class="fw-semibold">${e.itemName}</td>
                        <td class="text-muted">${e.serialNumber}</td>
                        <td><span class="status-badge ${badgeClass}">${e.status}</span></td>
                    </tr>
                `);
                row.hide().appendTo(tableBody).fadeIn(200);
            });
        } else {
            tableBody.append("<tr><td colspan='4' class='py-4 text-muted'>No records found in inventory</td></tr>");
        }
    }).fail(() => {
        $("#equipmentTableBody").html("<tr><td colspan='4' class='py-4 text-danger'>Error fetching equipment</td></tr>");
    });
}

// ================= SAVE EQUIPMENT =================
function saveEquipment() {
    const data = {
        itemName: $("#itemName").val().trim(),
        serialNumber: $("#serialNumber").val().trim(),
        status: $("#status").val(),
        equipmentImages: []
    };

    if (!data.itemName || !data.serialNumber) return alert("Item Name & Serial Number are required");

    $.ajax({
        url: `${BASE_URL}/save`,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: res => {
            alert(res.message || "Saved Successfully");
            resetForm();
            getAllEquipment();
        },
        error: err => alert("Error saving equipment: " + err.responseText)
    });
}

// ================= UPDATE EQUIPMENT =================
function updateEquipment() {
    const id = $("#equipmentId").val();
    if (!id) return alert("Select an item to update");

    const data = {
        id: parseInt(id),
        itemName: $("#itemName").val().trim(),
        serialNumber: $("#serialNumber").val().trim(),
        status: $("#status").val()
    };

    $.ajax({
        url: `${BASE_URL}/update`,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: res => {
            alert(res.message || "Updated Successfully");
            resetForm();
            getAllEquipment();
        },
        error: err => alert("Error updating equipment: " + err.responseText)
    });
}

// ================= DELETE EQUIPMENT =================
function deleteEquipment() {
    const id = $("#equipmentId").val();
    if (!id) return alert("Select an item first");
    if (!confirm("Permanently remove this equipment?")) return;

    $.ajax({
        url: `${BASE_URL}/delete/${id}`,
        method: "DELETE",
        success: res => {
            if (res.code === "00" || res.code === "SUCCESS") {
                // Remove row instantly from table
                $(`#row-${id}`).fadeOut(200, function() { $(this).remove(); });
                alert(res.message || "Deleted Successfully");
                resetForm();

                // If table empty, show placeholder
                if ($("#equipmentTableBody tr").length === 0) {
                    $("#equipmentTableBody").append("<tr><td colspan='4' class='py-4 text-muted'>No records found in inventory</td></tr>");
                }
            } else {
                alert(res.message || "Deletion failed");
            }
        },
        error: err => {
            alert("Server Error: " + err.responseText);
        }
    });
}

// ================= SELECT EQUIPMENT =================
function selectEquipment(id, name, sn, status) {
    $(".tr-clickable").removeClass("selected-row");
    $(`#row-${id}`).addClass("selected-row");

    $("#equipmentId").val(id);
    $("#itemName").val(name);
    $("#serialNumber").val(sn);
    $("#status").val(status);
    $("#formHeader").text("Editing: " + name);
}

// ================= RESET FORM =================
function resetForm() {
    $(".tr-clickable").removeClass("selected-row");
    $("#equipmentId").val("");
    $("#itemName").val("");
    $("#serialNumber").val("");
    $("#status").val("AVAILABLE");
    $("#formHeader").text("Equipment Details");
}
