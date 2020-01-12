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

@WebServlet("/searchvehicle")
public class SearchVehicle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String registrationNo = request.getParameter("registrationNo");

        VehicleDao vehicleDao = new VehicleDao();
        Vehicle[] vehicles = vehicleDao.findVehicleByRegNo(registrationNo);

        request.setAttribute("registrationNo", registrationNo);
        request.setAttribute("vehicles", vehicles);

        getServletContext().getRequestDispatcher("/vehicle/vehicleSearch.jsp")
                .forward(request, response);
    }
}
