<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>

    <!-- Stylesheets
    ============================================= -->
    <link href="https://fonts.googleapis.com/css?family=Lato&subset=latin-ext|Raleway:300,400,500,600,700|Crete+Round:400i"
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
                <li class="breadcrumb-item"><a href="#">Pracownicy</a></li>
                <li class="breadcrumb-item active"><a href="#">Szczegóły pracownika</a></li>
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
                        <h4>Szczegóły pracownika</h4>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="card">
                                <c:if test="${not empty employee.avatarFileName}"><img class="card-img-top" src="..${employee.avatarFileName}" alt="Card image"
                                     style="width:50%"></c:if>
                                <div class="card-body">
                                    <h4 class="card-title">${employee.firstName} ${employee.lastName}</h4>
                                    <p class="card-text">
                                    <table class="table table-borderless">
                                        <tbody>
                                        <tr>
                                            <td><strong>Kod pocztowy</strong></td>
                                            <td>${employee.zipCode}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Miasto</strong></td>
                                            <td>${employee.city}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Ulica</strong></td>
                                            <td>${employee.street}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Nr budynku</strong></td>
                                            <td>${employee.building}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Nr mieszkania</strong></td>
                                            <td>${employee.apartment}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Telefon</strong></td>
                                            <td>${employee.phone}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Koszt roboczogodziny</strong></td>
                                            <td>${fn:replace(employee.manHourCost, '.', ',')} zł</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Notes</strong></td>
                                            <td>${employee.notes}</td>
                                        </tr>
                                        </tbody>
                                    </table>


                                    </p>
                                    <a href="${pageContext.request.contextPath}/updateavatar?employeeId=${employee.id}" class="button button-mini button-blue button-3d">Avatar - update</a>
                                    <a href="/editemployee?employeeId=${employee.id}" class="button button-mini button-blue button-3d">Edytuj dane</a>
                                    <a href="/ordersemployee?employeeId=${employee.id}" class="button button-mini button-blue button-3d">Zlecenia</a>
                                    <a href="javascript:history.back()" class="button button-mini button-blue button-3d">Powrót</a>
                                </div>
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