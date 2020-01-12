package pl.coderslab.orders;

import pl.coderslab.Employee;
import pl.coderslab.Order;
import pl.coderslab.dao.EmployeeDao;
import pl.coderslab.dao.OrderDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ordersemployee")
public class OrdersEmployee extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));

        OrderDao orderDao = new OrderDao();
        Order[] orders = orderDao.findAllByEmployee(employeeId);

        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.read(employeeId);

        request.setAttribute("orders", orders);
        request.setAttribute("employee", employee);

        getServletContext().getRequestDispatcher("/order/orderEmployee.jsp")
                .forward(request, response);
    }
}
