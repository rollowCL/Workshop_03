package pl.coderslab.customer;

import pl.coderslab.Customer;
import pl.coderslab.dao.CustomerDao;

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
import java.util.Set;

@WebServlet("/addcustomer")
public class AddCustomer extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String birthDate = request.getParameter("birthDate");

        String errors = "";
        CustomerDao customerDao = new CustomerDao();
        Customer customer = new Customer(firstName, lastName, birthDate, email);

        if (customerDao.customerExists(email) != 0) {
            errors += "<ul><li>" + " Klient o takim emailu już istnieje w bazie!" + "</li></ul>";
            request.setAttribute("customer", customer);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/customer/customerAdd.jsp").forward(request, response);
        } else {

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

            if (!constraintViolations.isEmpty()) {
                errors = "<ul>";
                for (ConstraintViolation<Customer> constraintViolation : constraintViolations) {
                    errors += "<li>" + " " + constraintViolation.getMessage()
                            + "</li>";
                }
                errors += "</ul>";
                request.setAttribute("customer", customer);
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("customer/customerAdd.jsp").forward(request, response);
            } else {
                Customer customerCreated = customerDao.create(customer);
                request.setAttribute("customer", customerCreated);
                getServletContext().getRequestDispatcher("/customer/customerAddSuccess.jsp")
                        .forward(request, response);
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/customer/customerAdd.jsp")
                .forward(request, response);
    }
}
