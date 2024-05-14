<%-- 
    Document   : damageReportReview
    Created on : 2024年5月14日, 下午1:01:57
    Author     : boscochuen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
     <jsp:include page="header.jsp"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Damage Report Review</title>
    <link href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="js/damageReports.js"></script> <!-- Link to the external JS file -->
    <link href="css/damageReportReview.css" rel="stylesheet">
    <style>
        .table {
            width: 100%;
            border-collapse: collapse;
        }
        .table, .table th, .table td {
            border: 1px solid black;
        }
        .table th, .table td {
            padding: 8px;
            text-align: left;
        }
        .table th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Damage Reports</h1>
    <div id="dataContainer">Loading data...</div> <!-- Data display area -->
</body>
</html>
