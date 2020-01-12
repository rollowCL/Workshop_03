package pl.coderslab.orders;

import pl.coderslab.Employee;
import pl.coderslab.Order;
import pl.coderslab.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@WebServlet("/priceorder")
public class PriceOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        String customerCostString = request.getParameter("customerCost");
        String partsCostString = request.getParameter("partsCost");

        String errors = "<ul>";

        if (!Order.isValidNumber(customerCostString)) {
            errors += "<li>" + " Błędny koszt dla klienta - nieprawidłowa liczba" + "</li>";
            request.setAttribute("customerCostString", customerCostString);

        }

        if (!Order.isValidNumber(partsCostString)) {
            errors += "<li>" + " Błędny koszt części - nieprawidłowa liczba" + "</li>";
            request.setAttribute("partsCostString", partsCostString);

        }

        if (employeeId == 0) {
            errors += "<li>" + " Wybierz pracownika" + "</li>";
            request.setAttribute("employeeId", employeeId);
        }

        OrderDao orderDao = new OrderDao();
        Order order = null;

        try {
            Locale.setDefault(new Locale("pl", "PL"));
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            df.setParseBigDecimal(true);
            BigDecimal customerCost = (BigDecimal) df.parse(customerCostString);
            BigDecimal partsCost = (BigDecimal) df.parse(partsCostString);
            EmployeeDao employeeDao = new EmployeeDao();
            Employee employee = employeeDao.read(employeeId);
            order = new Order(orderId, customerCost, partsCost,employee);

        } catch (ParseException e) {
            orderDao = new OrderDao();
            order = orderDao.read(orderId);

            request.setAttribute("order", order);
            request.setAttribute("customerCostString", customerCostString);
            request.setAttribute("partsCostString", partsCostString);
        }


        errors += Order.validateOrderCost(order);
        errors += "</ul>";


        if ("<ul></ul>".equals(errors)) {
            EmployeeDao employeeDao = new EmployeeDao();
            Employee employee = employeeDao.read(employeeId);
            orderDao.priceOrder(order, employee);

            Order orderUpdated = orderDao.read(order.getId());
            request.setAttribute("order", orderUpdated);
            getServletContext().getRequestDispatcher("/order/orderDetails.jsp")
                    .forward(request, response);

        } else {

            EmployeeDao employeeDao = new EmployeeDao();
            Employee[] employees = employeeDao.findAll();
            request.setAttribute("employees", employees);

            request.setAttribute("employeeId", employeeId);
            Order orderNotUpdated = orderDao.read(order.getId());
            request.setAttribute("orderId", order.getId());
            request.setAttribute("order", orderNotUpdated);

            request.setAttribute("errors", errors);

            request.getRequestDispatcher("/order/orderPrice.jsp").forward(request, response);

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDao orderDao = new OrderDao();
        Order order = orderDao.read(orderId);
        request.setAttribute("order", order);

        EmployeeDao employeeDao = new EmployeeDao();
        Employee[] employees = employeeDao.findAll();
        request.setAttribute("employees", employees);

        getServletContext().getRequestDispatcher("/order/orderPrice.jsp")
                .forward(request, response);
    }
}
