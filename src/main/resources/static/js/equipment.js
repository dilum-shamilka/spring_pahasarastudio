const BASE_URL = "http://localhost:8080/api/v1/equipment";

$(document).ready(() => {
    getAllEquipment();

    $("#btnSave").click(saveEquipment);
    $("#btnUpdate").click(updateEquipment);
    $("#btnDelete").click(deleteEquipment);
    $("#btnClear").click(resetForm);
});

function getAllEquipment() {
    $.get(`${BASE_URL}/getAll`, (response) => {
        const tableBody = $("#equipmentTableBody");
        tableBody.empty();

        const equipment = response.content;
        if (equipment && equipment.length > 0) {
            equipment.forEach(e => {
                const badgeClass = e.status === 'AVAILABLE' ? 'badge-available' :
                    e.status === 'REPAIRING' ? 'badge-repairing' : 'badge-retired';
                tableBody.append(`
                    <tr id="row-${e.id}" class="tr-clickable" onclick="selectEquipment(${e.id}, '${e.itemName}', '${e.serialNumber}', '${e.status}')">
                        <td class="ps-4 fw-bold text-muted">#${e.id}</td>
                        <td class="fw-semibold">${e.itemName}</td>
                        <td class="text-muted">${e.serialNumber}</td>
                        <td><span class="status-badge ${badgeClass}">${e.status}</span></td>
                    </tr>
                `);
            });
        } else {
            tableBody.append("<tr><td colspan='4' class='py-4 text-muted'>No records found in inventory</td></tr>");
        }
    });
}

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
        success: res => { alert(res.message); getAllEquipment(); resetForm(); }
    });
}

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
        success: res => { alert(res.message); getAllEquipment(); resetForm(); }
    });
}

function deleteEquipment() {
    const id = $("#equipmentId").val();
    if (!id) return alert("Select an item first");
    if (!confirm("Permanently remove this equipment?")) return;

    $.ajax({
        url: `${BASE_URL}/delete/${id}`,
        method: "DELETE",
        success: res => { alert("Deleted Successfully"); getAllEquipment(); resetForm(); }
    });
}

function selectEquipment(id, name, sn, status) {
    $(".tr-clickable").removeClass("selected-row");
    $(`#row-${id}`).addClass("selected-row");

    $("#equipmentId").val(id);
    $("#itemName").val(name);
    $("#serialNumber").val(sn);
    $("#status").val(status);
    $("#formHeader").text("Editing: " + name);
}

function resetForm() {
    $(".tr-clickable").removeClass("selected-row");
    $("#equipmentId").val("");
    $("#itemName").val("");
    $("#serialNumber").val("");
    $("#status").val("AVAILABLE");
    $("#formHeader").text("Equipment Details");
}
