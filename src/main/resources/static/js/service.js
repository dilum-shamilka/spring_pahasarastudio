const BASE_URL = "http://localhost:8080/api/v1/services";
const TOKEN_KEY = "jwt_token"; // Unified key for Pahasara Studio

$(document).ready(function () {
    const token = localStorage.getItem(TOKEN_KEY);

    // 1. Initial Auth Check
    if (!token || token === "undefined") {
        window.location.href = "index.html";
        return;
    }

    // 2. Global AJAX Configuration
    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        error: function(xhr) {
            if (xhr.status === 401 || xhr.status === 403) {
                alert("Session expired or Unauthorized access.");
                localStorage.clear();
                window.location.href = "index.html";
            }
        }
    });

    // 3. Page Initialization
    getAllServices();

    // 4. Event Listeners
    $("#serviceForm").on("submit", saveOrUpdateService);
    $("#resetBtn").click(resetForm);
});

// ------------------ GET ALL SERVICES ------------------
function getAllServices() {
    $.ajax({
        url: `${BASE_URL}/getAll`,
        method: "GET",
        success: function(response) {
            const services = response.content || response;
            let rows = "";
            const noData = $("#noData");

            if (services && services.length > 0) {
                noData.addClass("d-none");
                services.forEach(s => {
                    // Define status badge styling
                    const statusClass = s.status === 'ACTIVE' ? 'bg-success-subtle text-success' : 'bg-secondary-subtle text-secondary';

                    rows += `
                    <tr>
                        <td class="ps-4 text-muted">#${s.id}</td>
                        <td class="fw-bold">${s.serviceName}</td>
                        <td>${parseFloat(s.price).toLocaleString('en-US', {minimumFractionDigits: 2})}</td>
                        <td><small>${s.description || 'No description'}</small></td>
                        <td>
                            <span class="badge ${statusClass} border px-2 py-1">
                                ${s.status}
                            </span>
                        </td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-primary me-1"
                                    onclick="loadService(${s.id}, '${escapeQuotes(s.serviceName)}', ${s.price}, '${escapeQuotes(s.description)}', '${s.status}')">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteService(${s.id})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>`;
                });
            } else {
                noData.removeClass("d-none").text("No packages found.");
            }

            $("#serviceTableBody").html(rows);
        },
        error: function(xhr) {
            $("#noData").removeClass("d-none").text("Server error: " + xhr.status);
        }
    });
}

// ------------------ SAVE OR UPDATE ------------------
function saveOrUpdateService(e) {
    e.preventDefault();

    const serviceId = $("#serviceId").val();
    const serviceData = {
        serviceName: $("#serviceName").val(),
        price: parseFloat($("#price").val()),
        description: $("#description").val(),
        status: $("#status").val()
    };

    // Determine if we are updating or saving
    const isUpdate = !!serviceId;
    const ajaxUrl = isUpdate ? `${BASE_URL}/update/${serviceId}` : `${BASE_URL}/save`;
    const ajaxMethod = isUpdate ? "PUT" : "POST";

    if (isUpdate) serviceData.id = parseInt(serviceId);

    $.ajax({
        url: ajaxUrl,
        method: ajaxMethod,
        contentType: "application/json",
        data: JSON.stringify(serviceData),
        success: function() {
            alert(`Package ${isUpdate ? 'updated' : 'saved'} successfully!`);
            getAllServices();
            resetForm();
        },
        error: function(xhr) {
            alert("Operation failed: " + (xhr.responseText || "Unknown error"));
        }
    });
}

// ------------------ LOAD INTO FORM ------------------
function loadService(id, serviceName, price, description, status) {
    $("#serviceId").val(id);
    $("#serviceName").val(serviceName);
    $("#price").val(price);
    $("#description").val(description);
    $("#status").val(status);

    // UI Update for Edit Mode
    $("#saveBtn").html('<i class="fas fa-sync me-2"></i>Update Package')
        .addClass("btn-primary").removeClass("btn-success");
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// ------------------ DELETE ------------------
function deleteService(id) {
    if(confirm(`Are you sure you want to delete package #${id}?`)) {
        $.ajax({
            url: `${BASE_URL}/delete/${id}`,
            method: "DELETE",
            success: function() {
                alert("Package deleted!");
                getAllServices();
            },
            error: function(xhr) {
                alert("Failed to delete: " + xhr.responseText);
            }
        });
    }
}

// ------------------ HELPERS ------------------
function resetForm() {
    $("#serviceId").val("");
    $("#serviceName, #price, #description").val("");
    $("#status").val("ACTIVE");
    $("#saveBtn").html('<i class="fas fa-save me-2"></i>Save Package')
        .addClass("btn-success").removeClass("btn-primary");
}

function escapeQuotes(str) {
    if (!str) return "";
    return str.replace(/'/g, "\\'").replace(/"/g, "&quot;");
}