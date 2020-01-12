package pl.coderslab.employee;

import pl.coderslab.Employee;
import pl.coderslab.dao.EmployeeDao;
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

@WebServlet("/editemployee")
public class EditEmployee extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String zipCode = request.getParameter("zipCode");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String building = request.getParameter("building");
        String apartment = request.getParameter("apartment");
        String phone = request.getParameter("phone");
        String manHourCostString = request.getParameter("manHourCost");
        String notes = request.getParameter("notes");

        Employee employee = null;
        String errors = "<ul>";


        if (!Employee.isValidNumber(manHourCostString)) {
            errors += "<li>" + " Błędny koszt roboczogodziny - nieprawidłowa liczba" + "</li>";
            request.setAttribute("manHourCostString", manHourCostString);

        }

        try {
            Locale.setDefault(new Locale("pl", "PL"));
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            df.setParseBigDecimal(true);
            BigDecimal manHourCost = (BigDecimal) df.parse(manHourCostString);
            employee = new Employee(employeeId, firstName, lastName, zipCode, city, street, building, apartment, phone, notes, manHourCost);
        } catch (ParseException e) {
            employee = new Employee(employeeId, firstName, lastName, zipCode, city, street, building, apartment, phone, notes);
            request.setAttribute("manHourCostString", manHourCostString);
        }

        errors += Employee.validateEmployee(employee);
        errors += "</ul>";

        if ("<ul></ul>".equals(errors)) {
            EmployeeDao employeeDao = new EmployeeDao();
            Employee employeeCreated = employeeDao.create(employee);
            request.setAttribute("customer", employeeCreated);
            getServletContext().getRequestDispatcher("/employee/employeeEditSuccess.jsp")
                    .forward(request, response);

        } else {
            request.setAttribute("employee", employee);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("employee/employeeEdit.jsp").forward(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.read(Integer.parseInt(request.getParameter("employeeId")));

        request.setAttribute("employee", employee);

        getServletContext().getRequestDispatcher("/employee/employeeEdit.jsp")
                .forward(request, response);
    }

}
