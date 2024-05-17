<%-- 
    Document   : checkOutStatistic
    Created on : 2024?5?17?, ??1:33:05
    Author     : boscochuen
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout and Location Statistics</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <style>
            .chart-container {
                max-width: 650px;
                height: 400px;
                margin: auto;
            }
            canvas {
                width: 100% !important;
                height: 100% !important;
            }
        </style>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <div class="container">
            <h1>Checkout and Location Statistics</h1>

            <div class="form-group">
                <label for="yearSelect">Select Year:</label>
                <select id="yearSelect" class="form-control">
                    <!-- Add options dynamically via JavaScript -->
                </select>
            </div>

            <h2>Checkout Statistics</h2>
            <div class="chart-container">
                <canvas id="checkoutChart"></canvas>
            </div>
            <h2>Bookings by Location</h2>
            <div class="chart-container">
                <canvas id="locationChart"></canvas>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                // Populate year select options
                var currentYear = new Date().getFullYear();
               $('#yearSelect').append('<option value="2024">2024</option>');
               $('#yearSelect').append('<option value="2023">2023</option>');
               $('#yearSelect').append('<option value="2022">2022</option>');
               
                var checkoutChart;
                var locationChart;

                function fetchStatistics(year) {
                    // Fetching checkout statistics
                    $.ajax({
                        url: 'CheckOutStatistic',
                        method: 'GET',
                        data: { year: year },
                        dataType: 'json',
                        success: function (data) {
                            var labels = [];
                            var checkouts = [];
                            data.forEach(function (stat) {
                                labels.push(stat.equipmentName);
                                checkouts.push(stat.checkouts);
                            });

                            var ctx = document.getElementById('checkoutChart').getContext('2d');
                            if (checkoutChart) {
                                checkoutChart.destroy();
                            }
                            checkoutChart = new Chart(ctx, {
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
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    scales: {
                                        y: {
                                            beginAtZero: true
                                        }
                                    }
                                }
                            });
                        },
                        error: function (xhr, status, error) {
                            console.error('AJAX error:', status, error);
                        }
                    });

                    // Fetching location statistics
                    $.ajax({
                        url: 'LocationStatisticServlet',
                        method: 'GET',
                        data: { year: year },
                        dataType: 'json',
                        success: function (data) {
                            var locations = [];
                            var counts = [];
                            data.forEach(function (stat) {
                                locations.push(stat.location);
                                counts.push(stat.bookingCount);
                            });

                            var ctx = document.getElementById('locationChart').getContext('2d');
                            if (locationChart) {
                                locationChart.destroy();
                            }
                            locationChart = new Chart(ctx, {
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
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    scales: {
                                        y: {
                                            beginAtZero: true,
                                            ticks: {
                                                precision: 0
                                            }
                                        }
                                    }
                                }
                            });
                        },
                        error: function (xhr, status, error) {
                            console.error('AJAX error for location stats:', status, error);
                        }
                    });
                }

                // Fetch initial data for the current year
                fetchStatistics(currentYear);

                // Update charts when the selected year changes
                $('#yearSelect').change(function () {
                    var selectedYear = $(this).val();
                    fetchStatistics(selectedYear);
                });
            });
        </script>
    </body>
</html>
