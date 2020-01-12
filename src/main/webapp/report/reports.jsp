<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <li class="breadcrumb-item"><a href="#">Raporty</a></li>
                <li class="breadcrumb-item active"><a href="#">Wybór raportu</a></li>
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
                        <h4>Wybór raportu</h4>
                        ${errors}
                    </div>
                </div>
                <c:if test="${empty reportData}">
                    <div>

                        <form class="row" action="${pageContext.request.contextPath}/reports" method="post">
                            <div class="col-12 form-group">

                                <p>
                                    <label>
                                        <input type="radio" name="reportType" value="1" <c:if test="${reportType eq 1}"> checked</c:if>>
                                        Raport przepracowanych godzin roboczych
                                    </label>
                                </p>

                                <p>
                                    <label>
                                        <input type="radio" name="reportType" value="2" <c:if test="${reportType eq 2}"> checked</c:if>>
                                        Raport zysku/strat
                                    </label>
                                </p>

                                <p>
                                    <label>
                                        <input type="radio" name="reportType" value="3" <c:if test="${reportType eq 3}"> checked</c:if>>
                                        Wykres 1. zlecenie po dniach
                                    </label>
                                </p>

                            </div>

                            <div class="col-md-6 form-group">
                                <label for="fromDate">Zlecenia od <small>*</small></label>
                                <input type="text" id="fromDate" name="fromDate" value="${fromDate}"
                                       class="form-control required"/>
                            </div>

                            <div class="col-md-6 form-group">
                                <label for="toDate">Zlecenia do <small>*</small></label>
                                <input type="text" id="toDate" name="toDate" value="${toDate}"
                                       class="form-control required"/>
                            </div>



                            <div class="w-100"></div>

                            <div class="col-12">
                                <button type="submit" class="button button-mini button-blue button-3d" value="Submit">
                                    Generuj
                                </button>
                                <input type="button" class="button button-mini button-blue button-3d" value="Powrót"
                                       onclick="history.back()">
                            </div>

                        </form>
                    </div>


                </c:if>
                <c:if test="${not empty reportData}">
                    <div class="table-responsive">
                        <table id="datatable1" class="table table-striped table-bordered" cellspacing="0" width="100%">
                            <thead class="thead-dark">
                            <tr>
                                <th style="width: 10%">Pracownik</th>
                                <th style="width: 10%">Liczba przepracowanych godzin</th>
                            </tr>
                            </thead>
                            <c:forEach items="${reportData}" var="item">
                                <tr>
                                    <td>${item.key}</td>
                                    <td>${item.value}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>
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