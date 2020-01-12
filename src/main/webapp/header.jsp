<%--
  Created by IntelliJ IDEA.
  User: osint
  Date: 12/30/19
  Time: 11:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Header
============================================= -->
<header id="header" class="full-header">

    <div id="header-wrap">

        <div class="container clearfix">

            <div id="primary-menu-trigger"><i class="icon-reorder"></i></div>

            <!-- Logo
            ============================================= -->
            <div id="logo">
                <a href="${pageContext.request.contextPath}/" class="standard-logo" data-dark-logo="images/bgsmall.jpg"><img
                        src="images/bgsmall.jpg" alt="Workshop Logo"></a>
            </div><!-- #logo end -->

            <!-- Primary Navigation
            ============================================= -->
            <nav id="primary-menu">

                <ul>
                    <li><a href="${pageContext.request.contextPath}/">
                        <div>Home</div>
                    </a>

                    </li>

                    <li><a href="#">
                        <div>Klienci</div>
                    </a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/customers">
                                <div>Wszyscy klienci</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/searchcustomer">
                                <div>Szukaj klienta</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/addcustomer">
                                <div>Dodaj nowego klienta</div>
                            </a></li>
                        </ul>
                    </li>

                    <li><a href="#">
                        <div>Pojazdy</div>
                    </a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/vehicles">
                                <div>Wszystkie pojazdy</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/searchvehicle">
                                <div>Szukaj pojazdu</div>
                            </a></li>
                        </ul>
                    </li>

                    <li><a href="#">
                        <div>Zlecenia</div>
                    </a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/orders">
                                <div>Wszytkie zlecenia</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/orders?statusId=2">
                                <div>Zlecenia do wyceny</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/orders?statusId=3">
                                <div>Zlecenia do rozpoczęcia</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/orders?statusId=4">
                                <div>Zlecenia do zakończenia</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/orders?statusId=5">
                                <div>Zlecenia do obioru</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/orders?statusId=6">
                                <div>Zlecenia odebrane</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/orders?statusId=7">
                                <div>Rezygnacje</div>
                            </a></li>
                        </ul>
                    </li>

                    <li><a href="#">
                        <div>Pracownicy</div>
                    </a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/employees">
                                <div>Wszyscy pracownicy</div>
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/addemployee">
                                <div>Dodaj pracownika</div>
                            </a></li>
                        </ul>
                    </li>

                    <li><a href="#">
                        <div>Raporty</div>
                    </a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/reports">
                                <div>Wybór raportu</div>
                            </a></li>
                        </ul>
                    </li>

                </ul>

                <!-- Top Search
                ============================================= -->
                <div id="top-search">
                    <a href="#" id="top-search-trigger"><i class="icon-search3"></i><i class="icon-line-cross"></i></a>
                    <form action="${pageContext.request.contextPath}/searchvehicle" method="get">
                        <input type="text" name="registrationNo" class="form-control" value=""
                               placeholder="Szukaj pojazdu...">
                    </form>
                </div><!-- #top-search end -->

            </nav><!-- #primary-menu end -->

        </div>

    </div>

</header>
<!-- #header end -->
