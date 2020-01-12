<%--
  Created by IntelliJ IDEA.
  User: osint
  Date: 1/2/20
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="table table-sm table-striped table-bordered">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Numer Klienta</th>
        <th scope="col">Imię</th>
        <th scope="col">Nazwisko</th>
        <th scope="col">Email</th>
        <th scope="col">Data urodzenia</th>
        <th scope="col">Przeglądaj</th>
        <th scope="col">Dodaj</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <c:forEach items="${customers}" var="customer">
        <tr>
            <td>${customer.id}</td>
            <td>${customer.firstName}</td>
            <td>${customer.lastName}</td>
            <td>${customer.email}</td>
            <td><c:if test="${customer.birthDate != 'null'}">${customer.birthDate}</c:if></td>
            <td>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/vehicles?customerId=${customer.id}">Pojazdy </a><br/>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/orderscustomer?customerId=${customer.id}">Zlecenia</a>
            </td>
            <td>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/addvehicle?customerId=${customer.id}">Pojazd </a>
            </td>
            <td>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/editcustomer?customerId=${customer.id}">Edytuj dane</a><br/>
                <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/delcustomer?customerId=${customer.id}">Usuń klienta</a><br/>
                <c:if test="${customer.email != 'null'}"><a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/emailcustomer?customerId=${customer.id}">Wyślij email</a></c:if>
            </td>
        </tr>
    </c:forEach>
</table>
