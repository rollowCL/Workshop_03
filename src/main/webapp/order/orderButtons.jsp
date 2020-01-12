<%--
  Created by IntelliJ IDEA.
  User: osint
  Date: 1/6/20
  Time: 11:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
    <c:when test="${order.status.id eq STATUS_ORDER_OPEN}">
        <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/priceorder?orderId=${order.id}"> Wyceń zlecenie </a>
    </c:when>
    <c:when test="${order.status.id eq STATUS_ORDER_PRICED}">
        <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/startorder?orderId=${order.id}"> Rozpocznij naprawę </a>
    </c:when>
    <c:when test="${order.status.id eq STATUS_ORDER_IN_PROGRESS}">
        <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/closeorder?orderId=${order.id}"> Zakończ zlecenie </a>
    </c:when>
    <c:when test="${order.status.id eq STATUS_ORDER_CLOSED}">
        <a class="button button-mini button-blue button-3d" href="${pageContext.request.contextPath}/submitorder?orderId=${order.id}"> Odbiór pojazdu </a>
    </c:when>
</c:choose>
<c:if test="${order.status.id < STATUS_ORDER_IN_PROGRESS}"><a class="button button-mini button-3d button-blue"
    href="${pageContext.request.contextPath}/cancelorder?orderId=${order.id}"> Rezygnacja</a></c:if>
