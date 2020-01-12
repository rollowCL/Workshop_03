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
					<li class="breadcrumb-item"><a href="#">Klienci</a></li>
					<li class="breadcrumb-item active"><a href="#">Edytuj dane klienta</a></li>
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
							<h4>Edycja danych klienta</h4>
                            ${errors}
						</div>

						<div>

							<form class="row"  action="editcustomer" method="post">

								<div class="col-md-6 form-group">
									<label for="firstname">Imię <small>*</small></label>
									<input type="text" id="firstname" name="firstname" value="${customer.firstName}" class="form-control required" />
								</div>

                                <div class="col-md-6 form-group">
									<label for="lastname">Nazwisko <small>*</small></label>
									<input type="text" id="lastname" name="lastname" value="${customer.lastName}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="email">Email <small>*</small></label>
									<input type="text" id="email" name="email" value="${customer.email}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="birthdate">Data urodzenia <small></small></label>
									<input type="text" id="birthdate" name="birthdate" value="${customer.birthDate}"
										   class="form-control required"/>
								</div>

								<div class="w-100"></div>
								<input type="hidden" name="customerId" value="${customer.id}"/>
								<div class="col-12">
									<button type="submit" class="button button-mini button-blue button-3d" value="Submit">Zapisz</button>
									<input type="button" class="button button-mini button-blue button-3d" value="Powrót" onclick="history.back()">
								</div>

							</form>
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