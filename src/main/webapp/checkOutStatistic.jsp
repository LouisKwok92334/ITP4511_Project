<%-- 
    Document   : checkOutStatistic
    Created on : 2024年5月17日, 上午1:33:05
    Author     : boscochuen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Checkout and Location Statistics</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="container">
        <h1>Checkout Statistics</h1>
        <canvas id="checkoutChart" width="400" height="200"></canvas>
        <h2>Bookings by Location</h2>
        <canvas id="locationChart" width="400" height="200"></canvas>
    </div>

    <script>
        $(document).ready(function() {
            // Fetching checkout statistics
            $.ajax({
                url: 'CheckOutStatistic',
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                    var labels = [];
                    var checkouts = [];
                    data.forEach(function(stat) {
                        labels.push(stat.equipmentName);
                        checkouts.push(stat.checkouts);
                    });

                    var ctx = document.getElementById('checkoutChart').getContext('2d');
                    var checkoutChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: '# of Checkouts',
                                data: checkouts,
                                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.error('AJAX error:', status, error);
                }
            });

            // Fetching location statistics
            $.ajax({
                url: 'LocationStatisticServlet',
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                    var locations = [];
                    var counts = [];
                    data.forEach(function(stat) {
                        locations.push(stat.location);
                        counts.push(stat.bookingCount); // Ensure this matches JSON object properties
                    });

                    var ctx = document.getElementById('locationChart').getContext('2d');
                    var locationChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: locations,
                            datasets: [{
                                label: 'Number of Bookings',
                                data: counts,
                                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                                borderColor: 'rgba(153, 102, 255, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                y: {  // Updated for Chart.js latest version
                                    beginAtZero: true,
                                    ticks: {
                                        precision: 0
                                    }
                                }
                            },
                            responsive: true,
                            maintainAspectRatio: false
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.error('AJAX error for location stats:', status, error);
                }
            });
        });
    </script>
</body>
</html>
