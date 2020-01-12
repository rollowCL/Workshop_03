<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <li class="breadcrumb-item"><a href="#">Klienci</a></li>
                <li class="breadcrumb-item active"><a href="#">Wyślij maila</a></li>
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
                        <h4>Wysyłanie maila do klienta ${customer.firstName} ${customer.lastName}</h4>
                        ${errors}
                    </div>

                    <div>
                        <c:if test="${not empty customer.email}">
                            <form class="row" action="${pageContext.request.contextPath}/emailcustomer" method="post">

                                <div class="col-md-12 form-group">
                                    <label for="subject">Tytuł wiadomości <small>*</small></label>
                                    <input type="text" id="subject" name="subject" value="${subject}"
                                           class="form-control required"/>
                                </div>

                                <div class="col-md-12 form-group">
                                    <label for="message">Wiadomość <small>*</small></label>
                                    <textarea type="text" id="message" name="message"
                                              class="form-control required">${message}</textarea>
                                </div>

                                <div class="col-md-12 form-group">
                                    <label for="fromEmail">Wyślij ze skrzynki <small>*</small></label>
                                    <input type="text" id="fromEmail" name="fromEmail" value="${fromEmail}"
                                           class="form-control required"/>
                                </div>

                                <div class="col-md-12 form-group">
                                    <label for="password">Hasło do emaila <small>*</small></label>
                                    <input type="password" id="password" name="password" value="${password}"
                                           class="form-control required"/>
                                </div>

                                <div class="col-md-12 form-group">
                                    <label for="smtpHost">Serwer SMTP <small>*</small></label>
                                    <input type="text" id="smtpHost" name="smtpHost" value="${smtpHost}"
                                           class="form-control required"/>
                                </div>


                                <div class="w-100"></div>
                                <input type="hidden" value="${customer.email}" name="email"/>
                                <input type="hidden" value="${customer.id}" name="customerId"/>
                                <div class="col-12">
                                    <button type="submit" class="button button-mini button-blue button-3d" value="Submit">Wyślij
                                    </button>
                                    <input type="button" class="button button-mini button-blue button-3d" value="Powrót"
                                           onclick="history.back()">
                                </div>

                            </form>
                        </c:if>
                        <c:if test="${empty customer.email}">
                            Brak maila
                        </c:if>
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