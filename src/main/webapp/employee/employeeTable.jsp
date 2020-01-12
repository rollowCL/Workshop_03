<%--
  Created by IntelliJ IDEA.
  User: osint
  Date: 1/2/20
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-sm table-striped table-bordered">
    <thead class="thead-dark">
    <tr>
        <th style="width: 15%">Imię</th>
        <th style="width: 20%">Nazwisko</th>
        <th style="width: 10%">Numer telefonu</th>
        <th style="width: 10%">Koszt roboczogodziny</th>
        <th style="width: 35%"></th>
    </tr>
    </thead>
    <c:forEach items="${employees}" var="employee">
        <tr>
            <td>${employee.firstName}</td>
            <td>${employee.lastName}</td>
            <td>${employee.phone}</td>
            <td>${fn:replace(employee.manHourCost, '.', ',')} zł</td>
            <td>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/employeedetails?employeeId=${employee.id}">Szczegóły </a>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/ordersemployee?employeeId=${employee.id}">Zlecenia </a>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/editemployee?employeeId=${employee.id}">Edytuj dane </a>
                </td>
        </tr>
    </c:forEach>
</table>
