/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function() {
    fetchBookings();

    function fetchBookings() {
        $.ajax({
            url: 'AcceptBookingServlet',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                let tableContent = '<tr><th>Booking ID</th><th>User ID</th><th>Equipment ID</th><th>Start Time</th><th>End Time</th><th>Delivery Location</th><th>Status</th><th>Actions</th></tr>';
                $.each(data, function(index, booking) {
                    tableContent += '<tr>' +
                        '<td>' + booking.bookingId + '</td>' +
                        '<td>' + booking.userId + '</td>' +
                        '<td>' + booking.equipmentId + '</td>' +
                        '<td>' + booking.startTime + '</td>' +
                        '<td>' + booking.endTime + '</td>' +
                        '<td>' + booking.deliveryLocation + '</td>' +
                        '<td>' + booking.status + '</td>' +
                        '<td><button class="editBtn" data-id="' + booking.bookingId + '">Edit</button></td>' +
                        '</tr>';
                });
                $('#bookingTable').html(tableContent);
                bindEditButtons();
            },
            error: function() {
                alert('Failed to fetch bookings');
            }
        });
    }

    function bindEditButtons() {
        $('.editBtn').on('click', function() {
            let bookingId = $(this).data('id');
            $('#bookingId').val(bookingId);
            $('#editModal').show();
        });
    }

    $('#updateStatusBtn').on('click', function() {
        let bookingId = $('#bookingId').val();
        $.ajax({
            url: 'AcceptBookingServlet',
            type: 'POST',
            data: {
                bookingId: bookingId,
                status: 'approved'
            },
            success: function() {
                alert('Status updated successfully');
                $('#editModal').hide();
                fetchBookings();
            },
            error: function() {
                alert('Failed to update status');
            }
        });
    });

    $('#closeModalBtn').on('click', function() {
        $('#editModal').hide();
    });
});
