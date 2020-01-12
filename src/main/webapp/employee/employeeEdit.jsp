<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
					<li class="breadcrumb-item"><a href="#">Pracownicy</a></li>
					<li class="breadcrumb-item active"><a href="#">Edytuj dane pracownika</a></li>
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
							<h4>Edycja danych pracownika</h4>
                            ${errors}
						</div>

						<div>

							<form class="row"  action="${pageContext.request.contextPath}/editemployee" method="post">

								<div class="col-md-6 form-group">
									<label for="firstName">Imię <small>*</small></label>
									<input type="text" id="firstName" name="firstName" value="${employee.firstName}" class="form-control required" />
								</div>

                                <div class="col-md-6 form-group">
									<label for="lastName">Nazwisko <small>*</small></label>
									<input type="text" id="lastName" name="lastName" value="${employee.lastName}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="zipCode">Kod pocztowy <small>*</small></label>
									<input type="text" id="zipCode" name="zipCode" value="${employee.zipCode}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="city">Miasto <small>*</small></label>
									<input type="text" id="city" name="city" value="${employee.city}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="street">Ulica <small>*</small></label>
									<input type="text" id="street" name="street" value="${employee.street}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="building">Budynek <small></small></label>
									<input type="text" id="building" name="building" value="${employee.building}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="phone">Numer telefonu <small>*</small></label>
									<input type="text" id="phone" name="phone" value="${employee.phone}" class="form-control required" />
								</div>

								<div class="col-md-6 form-group">
									<label for="manHourCost">Koszt roboczogodziny <small>*</small></label>
									<input type="text" id="manHourCost" name="manHourCost"
											<c:if test="${empty manHourCostString}"> value="${fn:replace(employee.manHourCost, '.', ',')}"</c:if>
											<c:if test="${not empty manHourCostString}"> value="${fn:replace(manHourCostString, '.', ',')}"</c:if>
										   class="form-control required" />
								</div>


								<div class="col-md-6 form-group">
									<label for="notes">Notatki <small></small></label>
									<textarea rows="5" id="notes" name="notes" class="form-control required">${employee.notes}</textarea>
								</div>



								<div class="w-100"></div>
								<input type="hidden" name="employeeId" value="${employee.id}"/>
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