<%--
  Created by IntelliJ IDEA.
  User: osint
  Date: 1/2/20
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="table-responsive">
    <table id="datatable1" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead class="thead-dark">
        <tr>
            <th style="width: 10%">Data zlecenia</th>
            <th style="width: 10%">Numer</th>
            <th style="width: 10%">Status</th>
            <c:choose>
                <c:when test="${empty statusId}">

                </c:when>
                <c:when test="${statusId eq STATUS_ORDER_OPEN}">
                    <th>Planowana data rozpoczęcia naprawy</th>
                </c:when>
                <c:when test="${statusId eq STATUS_ORDER_PRICED}">
                    <th>Planowana data rozpoczęcia naprawy</th>
                </c:when>
                <c:when test="${statusId eq STATUS_ORDER_CANCELLED}">
                    <th>Powód rezygnacji</th>
                </c:when>
                <c:otherwise>
                    <th>Data rozpoczęcia naprawy</th>
                </c:otherwise>
            </c:choose>
            <th>Pojazd</th>
            <th>Pracownik</th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.orderDate}</td>
                <td>${order.orderNumber}</td>
                <td>${order.status.statusName}</td>
                <c:choose>
                    <c:when test="${empty statusId}">

                    </c:when>
                    <c:when test="${order.status.id eq STATUS_ORDER_OPEN}">
                        <td>${order.plannedRepairDateStart}</td>
                    </c:when>
                    <c:when test="${order.status.id eq STATUS_ORDER_PRICED}">
                        <td>${order.plannedRepairDateStart}</td>
                    </c:when>
                    <c:when test="${order.status.id eq STATUS_ORDER_CANCELLED}">
                        <td>${order.orderCancelReason.cancelReasonName}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${fn:substring(order.actualRepairDateStart, 0, 16)}</td>
                    </c:otherwise>
                </c:choose>
                <td><a title="Pokaż dane samochodu"
                       href="${pageContext.request.contextPath}/searchvehicle?registrationNo=${order.vehicle.id}">${order.vehicle.registrationNo}</a>
                </td>
                <td>
                    <c:if test="${not empty order.employee}">
                    <a title="Pokaż dane pracownika"
                       href="${pageContext.request.contextPath}/employeedetails?employeeId=${order.employee.id}">${order.employee.firstName} ${order.employee.lastName}</a>
                </c:if>
                </td>
                <td>
                    <a class="button button-mini button-blue button-3d" title="Pokaż szczegóły zlecenia"
                       href="${pageContext.request.contextPath}/orderdetails?orderId=${order.id}">Szczegóły </a>
                    <%@ include file="orderButtons.jsp" %>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
