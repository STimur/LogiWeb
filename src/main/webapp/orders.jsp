<%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/17/2016
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders Page</title>
    <link rel="stylesheet" href="css/bootstrap/flatly.css">
</head>
<body>
<div id="ordersTable" class="container">
    <h2>Orders Table</h2>
    <p>Current orders:</p>
    <table class="table table-bordered table-striped table-hover">
        <thead>
        <tr>
            <th>Id</th>
            <th>Ready</th>
            <th>Route</th>
            <th>Truck</th>
            <th>Drivers</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>No</td>
            <td>
                <ol>
                    <li>SPb Load CargoA</li>
                    <li>SPb Unload CargoA</li>
                </ol>
            </td>
            <th>AA12345</th>
            <th>
                <ol>
                    <li>1 John Ivanov</li>
                    <li>2 Max Petrov</li>
                </ol>
            </th>
        </tr>
        <tr>
            <td>1</td>
            <td>No</td>
            <td>
                <ol>
                    <li>SPb Load CargoA</li>
                    <li>SPb Unload CargoA</li>
                </ol>
            </td>
            <th>AA12345</th>
            <th>
                <ol>
                    <li>1 John Ivanov</li>
                    <li>2 Max Petrov</li>
                </ol>
            </th>
        </tr>
        <tr>
            <td>1</td>
            <td>No</td>
            <td>
                <ol>
                    <li>SPb Load CargoA</li>
                    <li>SPb Unload CargoA</li>
                </ol>
            </td>
            <th>AA12345</th>
            <th>
                <ol>
                    <li>1 John Ivanov</li>
                    <li>2 Max Petrov</li>
                </ol>
            </th>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
