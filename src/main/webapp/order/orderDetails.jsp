<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>

    <!-- Stylesheets
    ============================================= -->
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,400i,700|Raleway:300,400,500,600,700|Crete+Round:400i"
          rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../css/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="../style.css" type="text/css"/>
    <link rel="stylesheet" href="../css/dark.css" type="text/css"/>
    <link rel="stylesheet" href="../css/font-icons.css" type="text/css"/>
    <link rel="stylesheet" href="../css/animate.css" type="text/css"/>
    <link rel="stylesheet" href="../css/magnific-popup.css" type="text/css"/>

    <link rel="stylesheet" href="../css/responsive.css" type="text/css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <!-- Document Title
    ============================================= -->
    <title>CRM | Mechanik</title>

</head>

<body class="stretched">

<!-- Document Wrapper
============================================= -->
<div id="wrapper" class="clearfix">
    <%@ include file="../header.jsp" %>
    <!-- Page Title
    ============================================= -->
    <section id="page-title" class="page-title-mini">

        <div class="container clearfix">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item"><a href="#">Zlecenia</a></li>
                <li class="breadcrumb-item active"><a href="#">Szczegóły zlecenia</a></li>
            </ol>
        </div>

    </section><!-- #page-title end -->

    <!-- Content
    ============================================= -->
    <section id="content">

        <div class="content-wrap">

            <div class="container clearfix">

                <div class="col_full nobottommargin">

                    <div class="heading-block nobottomborder">
                        <h4>Szczegóły zlecenia ${order.orderNumber} dla
                            pojazdu ${vehicle.manufacturer} ${vehicle.model} ${vehicle.registrationNo}</h4>
                    </div>

                    <div class="tabs clearfix" id="tabs">

                        <ul class="tab-nav clearfix">
                            <li><a href="#tab-1">Zlecenie</a></li>
                            <li><a href="#tab-2">Koszty</a></li>
                            <li><a href="#tab-3">Pojazd</a></li>
                            <li><a href="#tab-4">Klient</a></li>
                        </ul>

                        <div class="tab-container">

                            <div class="tab-content clearfix" id="tab-1">
                                <c:if test="${not empty order}">
                                    <div class="col-6">
                                        <table class="table table-borderless">
                                            <tr>
                                                <td><strong>Data zlecenia</strong></td>
                                                <td>${order.orderDate}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Status</strong></td>
                                                <td>${order.status.statusName}</td>
                                            </tr>
                                            <c:if test="${not empty order.employee}">
                                                <tr>
                                                    <td><strong>Przydzielony pracownik</strong></td>
                                                    <td>${order.employee.firstName} ${order.employee.lastName}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${empty order.actualRepairDateStart}">
                                                <tr>
                                                    <td><strong>Planowana data rozpoczęcia naprawy</strong></td>
                                                    <td>${order.plannedRepairDateStart}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${not empty order.actualRepairDateStart}">
                                                <tr>
                                                    <td><strong>Data rozpoczęcia naprawy</strong></td>
                                                    <td>${fn:substring(order.actualRepairDateStart, 0, 16)}</td>
                                                </tr>
                                            </c:if>
                                            <tr>
                                                <td><strong>Opis problemu</strong></td>
                                                <td>${order.problemDesc}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Opis naprawy</strong></td>
                                                <td>${order.repairDesc}</td>
                                            </tr>
                                        </table>
                                        <%@ include file="orderButtons.jsp" %>
                                    </div>
                                </c:if>
                            </div>
                            <div class="tab-content clearfix" id="tab-2">
                                <c:if test="${not empty order}">
                                    <div class="col-6">
                                        <table class="table table-borderless">
                                            <tr>
                                                <td><strong>Koszt dla klienta</strong></td>
                                                <td>${fn:replace(order.customerCost, '.', ',')} zł</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Koszt części</strong></td>
                                                <td>${fn:replace(order.partsCost, '.', ',')} zł</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Koszt roboczogodziny</strong></td>
                                                <td>${fn:replace(order.employeeManHourCost, '.', ',')} zł</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Liczba roboczogodzin</strong></td>
                                                <td>${fn:replace(order.manHoursSpent, '.', ',')}</td>
                                            </tr>
                                        </table>
                                        <c:if test="${empty order.customerCost}"><a
                                                class="button button-mini button-blue button-3d"
                                                href="/priceorder?orderId=${order.id}">Wyceń
                                            naprawę</a></c:if>
                                        <c:if test="${not empty order.customerCost}"><a
                                                class="button button-mini button-blue button-3d"
                                                href="/priceorder?orderId=${order.id}">Zaktualizuj wycenę</a></c:if>

                                    </div>
                                </c:if>
                            </div>
                            <div class="tab-content clearfix" id="tab-3">
                                <c:if test="${not empty vehicle}">
                                    <div class="col-6">
                                        <table class="table table-borderless">
                                            <tr>
                                                <td><strong>Producent</strong></td>
                                                <td>${vehicle.manufacturer}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Model</strong></td>
                                                <td>${vehicle.model}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Rok produkcji</strong></td>
                                                <td>${vehicle.productionYear}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Numer rejestracyjny</strong></td>
                                                <td>${vehicle.registrationNo}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Data kolejnego badania technicznego</strong></td>
                                                <td>${vehicle.nextTechnicalCheckDate}</td>
                                            </tr>
                                        </table>
                                        <a class="button button-mini button-blue button-3d"
                                           href="/ordersvehicle?vehicleId=${vehicle.id}">
                                            Zlecenia</a>
                                        <a class="button button-mini button-blue button-3d"
                                           href="/addorder?vehicleId=${vehicle.id}">Dodaj
                                            zlecenie</a>
                                        <a class="button button-mini button-blue button-3d"
                                           href="/editvehicle?vehicleId=${vehicle.id}">Edytuj
                                        </a>
                                        <a class="button button-mini button-blue button-3d"
                                           href="/delvehicle?vehicleId=${vehicle.id}">Usuń
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                            <div class="tab-content clearfix" id="tab-4">
                                <c:if test="${not empty customer}">
                                    <div class="col-6">
                                        <table class="table table-borderless">
                                            <tr>
                                                <td><strong>Numer Klienta</strong></td>
                                                <td>${customer.id}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Imię i nazwisko</strong></td>
                                                <td>${customer.firstName} ${customer.lastName}</td>
                                            </tr>
                                            <tr>
                                                <td>Email</td>
                                                <td>${customer.email}</td>
                                            </tr>
                                            <tr>
                                                <td><strong>Data urodzenia</strong></td>
                                                <td><c:if test="${customer.birthDate != 'null'}">
                                                    ${customer.birthDate}</c:if></td>
                                            </tr>
                                        </table>
                                        <a class="button button-mini button-blue button-3d"
                                           href="${pageContext.request.contextPath}/editcustomer?customerId=${customer.id}">Edytuj
                                            dane</a>
                                        <a class="button button-mini button-blue button-3d"
                                           href="${pageContext.request.contextPath}/delcustomer?customerId=${customer.id}">Usuń
                                            klienta</a>
                                        <a class="button button-mini button-blue button-3d"
                                           href="${pageContext.request.contextPath}/addvehicle?customerId=${customer.id}">Dodaj
                                            pojazd</a>
                                        <br/>
                                        <a class="button button-mini button-blue button-3d"
                                           href="${pageContext.request.contextPath}/vehicles?customerId=${customer.id}">Pokaż
                                            pojazdy</a>
                                        <a class="button button-mini button-blue button-3d"
                                           href="${pageContext.request.contextPath}/orderscustomer?customerId=${customer.id}">Pokaż
                                            zlecenia</a><br/>
                                    </div>
                                </c:if>
                            </div>

                        </div>

                    </div>
                </div>


            </div>

        </div>

    </section><!-- #content end -->

    <%@ include file="../footer.jsp" %>

</div><!-- #wrapper end -->

<!-- Go To Top
============================================= -->
<div id="gotoTop" class="icon-angle-up"></div>

<!-- External JavaScripts
============================================= -->
<script src="../js/jquery.js"></script>
<script src="../js/plugins.js"></script>

<!-- Footer Scripts
============================================= -->
<script src="../js/functions.js"></script>

</body>
</html>