/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

var equipments = []; // Define globally

function loadInventory() {
    fetch('InventoryServlet?action=listAvailableJson')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                console.log("Data fetched successfully:", data);
                equipments = data; // Update global variable
                updateTable(data);
            })
            .catch(error => {
                console.error('Error fetching inventory:', error);
                document.getElementById('inventory').textContent = 'Failed to load data: ' + error.message;
            });
}


function updateTable(equipments) {
    console.log('Updating table with data:', equipments);
    let output = '<table border="1"><tr><th>ID</th><th>Name</th><th>Description</th><th>Total Quantity</th><th>Available Quantity</th><th>Status</th><th>Location</th><th>Staff Only</th><th>Actions</th></tr>';
    equipments.forEach(equipment => {
        output += '<tr>' +
                '<td>' + equipment.equipmentId + '</td>' +
                '<td>' + equipment.name + '</td>' +
                '<td>' + equipment.description + '</td>' +
                '<td>' + equipment.totalQuantity + '</td>' +
                '<td>' + equipment.availableQuantity + '</td>' +
                '<td>' + equipment.status + '</td>' +
                '<td>' + equipment.location + '</td>' +
                '<td>' + (equipment.staffOnly ? 'Yes' : 'No') + '</td>' +
                '<td><button onclick="editEquipment(' + equipment.equipmentId + ')">Edit</button></td>' +
                '</tr>';
    });
    output += '</table>';
    document.getElementById('inventory').innerHTML = output;
}



document.addEventListener("DOMContentLoaded", loadInventory);
const inventory = document.getElementById('inventory');

function editEquipment(equipmentId) {
     console.log("Edit button clicked for equipment ID:", equipmentId); // Check if this logs when you click the button
    const equipment = equipments.find(eq => eq.equipmentId === equipmentId);
    if (equipment) {
        const modal = document.getElementById('editModal');
        document.getElementById('editId').value = equipment.equipmentId;
        document.getElementById('editName').value = equipment.name;
        document.getElementById('editDescription').value = equipment.description;
        document.getElementById('editTotalQuantity').value = equipment.totalQuantity;
        document.getElementById('editAvailableQuantity').value = equipment.availableQuantity;
        document.getElementById('editStatus').value = equipment.status;
        document.getElementById('editLocation').value = equipment.location;
        document.getElementById('editStaffOnly').checked = equipment.staffOnly;
        modal.style.display = 'block';
    }
}

// Close the modal
function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}
function submitEdit() {
    const equipment = {
        equipmentId: parseInt(document.getElementById('editId').value),
        name: document.getElementById('editName').value,
        description: document.getElementById('editDescription').value,
        totalQuantity: parseInt(document.getElementById('editTotalQuantity').value),
        availableQuantity: parseInt(document.getElementById('editAvailableQuantity').value),
        status: document.getElementById('editStatus').value,
        location: document.getElementById('editLocation').value,
        staffOnly: document.getElementById('editStaffOnly').checked
    };

    // Make sure to check what is actually being sent in the console
    console.log(JSON.stringify(equipment));

  function handleError(error) {
    console.error('Network or server error:', error);
    alert('Error communicating with the server. Please try again later.');
}

// Usage in submitEdit
fetch('InventoryServlet?action=update', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(equipment)
})
.then(response => response.json())
.then(handleResponse)
.catch(handleError);

}
function handleResponse(response) {
    if (response.status === 'success') {
        console.log('Update successful!');
        alert('Update successful!');
    } else {
        console.error('Update failed:', response.message);
        alert('Update failed:', response.message);
    }
}
