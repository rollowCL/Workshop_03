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
                <li class="breadcrumb-item"><a href="#">Zlecenia</a></li>
                <li class="breadcrumb-item active"><a href="#">Wszystkie zlecenia</a></li>
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
                        <h4>Wszystkie zlecenia</h4>
                        <form class="row" action="${pageContext.request.contextPath}/orders" method="get">
                            <div class="form-group">
                                <select class="form-control" id="statusId" name="statusId">
                                    <option value="">---</option>
                                    <c:forEach items="${statuses}" var="status">
                                        <option value="${status.id}"<c:if
                                                test="${status.id eq statusId}"> selected</c:if>>${status.statusName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="button button-mini button-blue button-3d" value="Submit">Filtruj</button>
                        </form>
                    </div>
                </div>
                <c:if test="${empty orders}">Brak zleceń</c:if>
                <c:if test="${not empty orders}">
                    <div>
                        <%@ include file="orderTable.jsp" %>
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
<!-- Bootstrap Data Table Plugin -->
<script src="../js/components/bs-datatable.js"></script>
<script>

    $(document).ready(function() {
        $('#datatable1').dataTable();
    });

</script>
</body>
</html>