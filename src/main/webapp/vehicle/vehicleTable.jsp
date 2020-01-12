<%--
  Created by IntelliJ IDEA.
  User: osint
  Date: 1/2/20
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead class="thead-dark">
    <tr>
        <th scope="col" style="width: 5%">Producent</th>
        <th scope="col" style="width: 5%">Model</th>
        <th scope="col" style="width: 5%">Rok produkcji</th>
        <th scope="col" style="width: 10%">Numer rejestracyjny</th>
        <th scope="col" style="width: 10%">Data kolejnego badania technicznego</th>
        <th scope="col" style="width: 35%">Właściciel</th>
        <th scope="col" style="width: 15%"> </th>
    </tr>
    </thead>
    <c:forEach items="${vehicles}" var="vehicle">
        <tr>
            <td>${vehicle.manufacturer}</td>
            <td>${vehicle.model}</td>
            <td>${vehicle.productionYear}</td>
            <td>${vehicle.registrationNo}</td>
            <td>${vehicle.nextTechnicalCheckDate}</td>
            <td>${vehicle.customer.firstName} ${vehicle.customer.lastName}</td>
            <td>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/ordersvehicle?vehicleId=${vehicle.id}">Zobacz zlecenia </a>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/addorder?vehicleId=${vehicle.id}">Dodaj zlecenie</a><br/>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/editvehicle?vehicleId=${vehicle.id}">Edytuj pojazd </a>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/delvehicle?vehicleId=${vehicle.id}">Usuń pojazd</a>
            </td>
        </tr>
    </c:forEach>
</table>
