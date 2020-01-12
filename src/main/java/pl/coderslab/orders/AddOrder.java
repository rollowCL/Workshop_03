package pl.coderslab.orders;

import pl.coderslab.*;
import pl.coderslab.dao.*;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@WebServlet("/addorder")
public class AddOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date plannedRepaidDateStart = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            plannedRepaidDateStart = sdf.parse(request.getParameter("plannedRepairDateStart"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String problemDesc = request.getParameter("problemDesc");
        String repairDesc = request.getParameter("repairDesc");
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle vehicle = vehicleDao.read(vehicleId);
        request.setAttribute("vehicle", vehicle);
        OrderDao orderDao = new OrderDao();

        try {
            StatusDao statusDao = new StatusDao();
            Status status = statusDao.read(Config.STATUS_ORDER_OPEN);
            OrderNumberDao orderNumberDao = new OrderNumberDao();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            Integer number = orderNumberDao.readMax(year);
            if (number == null) {
                number = 0;
            }
            String orderNumberString = "" + ++number + "/" + year;
            Order order = new Order(orderNumberString,new Date(), plannedRepaidDateStart, null, problemDesc, repairDesc, null, null, null, null, status, vehicle);

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

            String errors = "";


            if (!constraintViolations.isEmpty()) {
                errors = "<ul>";
                for (ConstraintViolation<Order> constraintViolation : constraintViolations) {
                    errors += "<li>" + " " + constraintViolation.getMessage()
                            + "</li>";
                }
                errors += "</ul>";

                request.setAttribute("order", order);
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/order/orderAdd.jsp").forward(request, response);

            } else {
                OrderNumber orderNumber = new OrderNumber(year, number);
                orderNumberDao.create(orderNumber);
                Order orderCreated = orderDao.create(order);
                request.setAttribute("order", orderCreated);
                getServletContext().getRequestDispatcher("/order/orderAddSuccess.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errors", e.getMessage());
            request.getRequestDispatcher("/order/orderAdd.jsp").forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle vehicle = vehicleDao.read(vehicleId);

        request.setAttribute("vehicle", vehicle);

        getServletContext().getRequestDispatcher("/order/orderAdd.jsp")
                .forward(request, response);
    }
}
