package pl.coderslab.orders;

import pl.coderslab.Order;
import pl.coderslab.Vehicle;
import pl.coderslab.dao.OrderDao;
import pl.coderslab.dao.VehicleDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ordersvehicle")
public class OrdersVehicle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));

        OrderDao orderDao = new OrderDao();
        Order[] orders = null;
        orders = orderDao.findAllByVehicle(vehicleId);

        VehicleDao vehicleDao = new VehicleDao();
        Vehicle vehicle = vehicleDao.read(vehicleId);

        request.setAttribute("orders", orders);
        request.setAttribute("vehicle", vehicle);

        getServletContext().getRequestDispatcher("/order/orderVehicle.jsp")
                .forward(request, response);
    }
}
