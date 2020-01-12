package pl.coderslab.vehicle;

import pl.coderslab.Customer;
import pl.coderslab.Vehicle;
import pl.coderslab.dao.CustomerDao;
import pl.coderslab.dao.VehicleDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles")
public class AllVehicles extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle[] vehicles;
        Integer customerId = null;

        try {
            customerId = Integer.parseInt(request.getParameter("customerId"));
            vehicles = vehicleDao.findAllByCustomer(customerId);
            CustomerDao customerDao = new CustomerDao();
            Customer customer = customerDao.read(customerId);
            request.setAttribute("customer", customer);

        } catch (NumberFormatException e) {
            vehicles = vehicleDao.findAll();
        }

        request.setAttribute("vehicles", vehicles);

        getServletContext().getRequestDispatcher("/vehicle/vehicleAll.jsp")
                .forward(request, response);
    }
}
