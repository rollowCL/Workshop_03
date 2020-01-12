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
                <li class="breadcrumb-item active"><a href="#">Raport przepracowanych godzin roboczych</a></li>
            </ol>
        </div>

    </section><!-- #page-title end -->

    <!-- Content
    ============================================= -->
    <section id="content">

        <div class="content-wrap">

            <div class="container clearfix">

                <div class="col_full nobottommargin">

                    <div class="heading-block nobottomborder"><br/>
                        <h4>Raport przepracowanych godzin roboczych</h4>
                        <strong>Założenia raportu:</strong><br/>
                        <ul>
                            <li> przepracowane godziny przeliczone od momentu rozpoczęcia zlecenia do jego zakończeni</li>
                            <li> uwzględione godziny robocze wg konfiguracji</li>
                            <li> zlecenia otwarte przeliczone, jakby zakończone zostały teraz z uwzględnieniem godzin roboczych</li>
                        </ul>
                        ${errors}
                    </div>
                </div>
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
                <%@ include file="../backButton.jsp" %>
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