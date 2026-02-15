const API_BASE = "http://localhost:8080/api/v1/services";

// Fetch and display services
async function fetchServices() {
    try {
        const res = await fetch(`${API_BASE}/getAll`);
        const data = await res.json();
        const tbody = document.getElementById("serviceTableBody");
        tbody.innerHTML = "";

        data.forEach(service => {
            const statusClass = service.status === 'ACTIVE' ? 'bg-active' : 'bg-inactive';
            tbody.innerHTML += `
        <tr>
          <td class="ps-4 fw-bold">#${service.id}</td>
          <td class="fw-semibold">${service.serviceName}</td>
          <td>Rs. ${parseFloat(service.price).toLocaleString()}</td>
          <td class="text-muted small">${service.description || 'No description'}</td>
          <td><span class="status-badge ${statusClass}">${service.status}</span></td>
          <td class="text-center">
            <button class="btn btn-sm btn-outline-primary me-1" onclick="editService(${service.id})">Edit</button>
            <button class="btn btn-sm btn-outline-danger" onclick="deleteService(${service.id})">Delete</button>
          </td>
        </tr>`;
        });
    } catch (error) {
        console.error("Error fetching services:", error);
    }
}

// Add or Update Service
document.getElementById("serviceForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("serviceId").value;
    const serviceData = {
        serviceName: document.getElementById("serviceName").value,
        price: parseFloat(document.getElementById("price").value),
        description: document.getElementById("description").value,
        status: document.getElementById("status").value
    };

    const endpoint = id ? `${API_BASE}/update/${id}` : `${API_BASE}/save`;
    const method = id ? "PUT" : "POST";

    try {
        const res = await fetch(endpoint, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(serviceData)
        });
        const result = await res.json();
        alert(result.message);
        document.getElementById("resetBtn").click();
        fetchServices();
    } catch (error) {
        console.error("Error saving service:", error);
    }
});

// Reset form
document.getElementById("resetBtn").addEventListener("click", () => {
    document.getElementById("serviceId").value = "";
    document.getElementById("serviceName").value = "";
    document.getElementById("price").value = "";
    document.getElementById("description").value = "";
    document.getElementById("status").value = "ACTIVE";
    document.getElementById("saveBtn").innerText = "Save Service";
    document.getElementById("saveBtn").className = "btn btn-success btn-sm btn-action";
    document.getElementById("formHeader").innerText = "Register New Service";
});

// Edit service
async function editService(id) {
    const res = await fetch(`${API_BASE}/getAll`);
    const services = await res.json();
    const service = services.find(s => s.id === id);
    if(service) {
        document.getElementById("serviceId").value = service.id;
        document.getElementById("serviceName").value = service.serviceName;
        document.getElementById("price").value = service.price;
        document.getElementById("description").value = service.description || '';
        document.getElementById("status").value = service.status;

        document.getElementById("saveBtn").innerText = "Update Now";
        document.getElementById("saveBtn").className = "btn btn-primary btn-sm btn-action";
        document.getElementById("formHeader").innerText = "Update Service: " + service.serviceName;
    }
}

// Delete service
async function deleteService(id) {
    if(confirm("Are you sure?")) {
        const res = await fetch(`${API_BASE}/delete/${id}`, { method: "DELETE" });
        const result = await res.json();
        alert(result.message);
        fetchServices();
    }
}

// Initialize table on load
fetchServices();
