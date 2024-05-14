/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function() {
    $.ajax({
        url: 'damageReports', // Servlet URL that returns JSON
        type: 'GET',
        dataType: 'json',
        success: function(reports) {
            var html = '<table id="reportsTable" class="table">';
            html += '<thead><tr><th>Report ID</th><th>Equipment ID</th><th>Reported By</th><th>Description</th><th>Report Date</th><th>Status</th><th>Created At</th><th>Updated At</th></tr></thead>';
            html += '<tbody>';
            $.each(reports, function(index, report) {
                html += '<tr>';
                html += '<td>' + report.reportId + '</td>';
                html += '<td>' + report.equipmentId + '</td>';
                html += '<td>' + report.reportedBy + '</td>';
                html += '<td>' + report.description + '</td>';
                html += '<td>' + report.reportDate + '</td>';
                html += '<td>' + report.status + '</td>';
                html += '<td>' + report.createdAt + '</td>';
                html += '<td>' + report.updatedAt + '</td>';
                html += '</tr>';
            });
            html += '</tbody></table>';
            $('#dataContainer').html(html);
        },
        error: function() {
            $('#dataContainer').html('<p>An error has occurred</p>');
        }
    });
});

