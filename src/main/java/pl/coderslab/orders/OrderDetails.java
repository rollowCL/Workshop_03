package pl.coderslab.orders;

import pl.coderslab.*;
import pl.coderslab.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orderdetails")
public class OrderDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderDao orderDao = new OrderDao();
        Order order = orderDao.read(Integer.parseInt(request.getParameter("orderId")));
        System.out.println(order.toString());
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle vehicle = vehicleDao.read(order.getVehicle().getId());
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.read(vehicle.getCustomer().getId());
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.read(order.getEmployee().getId());

        request.setAttribute("order", order);
        request.setAttribute("vehicle", vehicle);
        request.setAttribute("customer", customer);
        request.setAttribute("employee", employee);


        getServletContext().getRequestDispatcher("/order/orderDetails.jsp")
                .forward(request, response);
    }
}
