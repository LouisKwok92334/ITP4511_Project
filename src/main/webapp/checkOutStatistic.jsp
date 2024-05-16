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
        <title>Checkout Statistics</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <div class="container">
            <h1>Checkout Statistics</h1>
            <canvas id="checkoutChart" width="400" height="200"></canvas>
        </div>
        
        <script>
            $(document).ready(function() {
                $.ajax({
                    url: 'CheckOutStatisticServlet',
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
                        var chart = new Chart(ctx, {
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
                                    yAxes: [{
                                        ticks: {
                                            beginAtZero: true
                                        }
                                    }]
                                }
                            }
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error('AJAX error:', status, error);
                    }
                });
            });
        </script>
    </body>
</html>
