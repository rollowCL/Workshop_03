<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

	<!-- Stylesheets
	============================================= -->
	<link href="https://fonts.googleapis.com/css?family=Lato&subset=latin-ext|Raleway:300,400,500,600,700|Crete+Round:400i" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="../css/bootstrap.css" type="text/css" />
	<link rel="stylesheet" href="../style.css" type="text/css" />
	<link rel="stylesheet" href="../css/dark.css" type="text/css" />
	<link rel="stylesheet" href="../css/font-icons.css" type="text/css" />
	<link rel="stylesheet" href="../css/animate.css" type="text/css" />
	<link rel="stylesheet" href="../css/magnific-popup.css" type="text/css" />

	<link rel="stylesheet" href="../css/responsive.css" type="text/css" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />

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
					<li class="breadcrumb-item"><a href="#">Pojazdy</a></li>
					<li class="breadcrumb-item active"><a href="#">Wszystkie pojazdy</a></li>
				</ol>
			</div>

		</section><!-- #page-title end -->

		<!-- Content
		============================================= -->
		<section id="content">

			<div class="content-wrap" >

				<div class="container clearfix">

					<div class="col_full nobottommargin">

						<div class="heading-block nobottomborder">
							<c:if test="${empty customer}"><h4>Wszystkie pojazdy</h4></c:if>
							<c:if test="${not empty customer}"><h4>Wszystkie pojazdy klienta ${customer.firstName} ${customer.lastName}</h4></c:if>
						</div>

						<c:if test="${empty vehicles}">
							<h4>Brak pojazdów</h4>
						</c:if>
						<c:if test="${not empty vehicles}">
							<div>
								<%@ include file="vehicleTable.jsp" %>
							</div>
						</c:if>

					</div>
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