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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@WebServlet("/editvehicle")
public class EditVehicle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String model = request.getParameter("model");
        String manufacturer = request.getParameter("manufacturer");
        String productionYearStr = request.getParameter("productionYear");

        if (productionYearStr == "" || productionYearStr == null) {
            productionYearStr = "0";
        }
        int productionYear = Integer.parseInt(productionYearStr);
        String registrationNo = request.getParameter("registrationNo");
        int customerId = Integer.parseInt(request.getParameter("customerId"));

        String nextTechnicalCheckDateStr = request.getParameter("nextTechnicalCheckDate");
        Date nextTechnicalCheckDate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            nextTechnicalCheckDate = sdf.parse(nextTechnicalCheckDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            CustomerDao customerDao = new CustomerDao();
            Customer customer = customerDao.read(customerId);
            VehicleDao vehicleDao = new VehicleDao();
            Vehicle vehicle = new Vehicle(model, manufacturer, productionYear, registrationNo, nextTechnicalCheckDate, customer);
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Vehicle>> constraintViolations = validator.validate(vehicle);
            request.setAttribute("customer", customer);
            String errors = "";

            if (vehicleDao.vehicleExists(registrationNo)) {
                errors += "<ul><li>" + " Pojazd o takim numerze rejestracyjnym już istnieje w bazie!" + "</li></ul>";
                request.setAttribute("vehicle", vehicle);
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/vehicle/vehicleEdit.jsp").forward(request, response);
            } else {

                if (!constraintViolations.isEmpty()) {
                    errors = "<ul>";
                    for (ConstraintViolation<Vehicle> constraintViolation : constraintViolations) {
                        errors += "<li>" + " " + constraintViolation.getMessage()
                                + "</li>";
                    }
                    errors += "</ul>";
                    request.setAttribute("vehicle", vehicle);
                    request.setAttribute("errors", errors);
                    request.getRequestDispatcher("/vehicle/vehicleEdit.jsp").forward(request, response);
                } else {
                    vehicleDao.update(vehicle);
                    request.setAttribute("vehicle", vehicle);
                    getServletContext().getRequestDispatcher("/vehicle/vehicleEditSuccess.jsp")
                            .forward(request, response);
                }
            }
        } catch (Exception e) {
            request.setAttribute("errors", e.getMessage());
            request.getRequestDispatcher("/vehicle/vehicleEdit.jsp").forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle vehicle = vehicleDao.read(vehicleId);

        int customerId = vehicle.getCustomer().getId();
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.read(customerId);

        request.setAttribute("customer", customer);
        request.setAttribute("vehicle", vehicle);

        getServletContext().getRequestDispatcher("/vehicle/vehicleEdit.jsp")
                .forward(request, response);
    }
}
