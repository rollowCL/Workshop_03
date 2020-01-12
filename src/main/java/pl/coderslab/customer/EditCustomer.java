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

@WebServlet("/editcustomer")
public class EditCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String birthdate = request.getParameter("birthdate");

        String errors = "";
        CustomerDao customerDao = new CustomerDao();
        Customer customer = new Customer(customerId, firstName, lastName, birthdate, email);
        System.out.println(customer.getId());

        if (customerDao.customerExists(email) != customerId && customerDao.customerExists(email) != 0) {
            errors += "<ul><li>" + " Klient o takim emailu już istnieje w bazie!" + "</li></ul>";
            request.setAttribute("customer", customer);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/customer/customerEdit.jsp").forward(request, response);

        } else {

            try {

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
                request.getRequestDispatcher("customer/customerEdit.jsp").forward(request, response);

            } else {

                customerDao.update(customer);
                request.setAttribute("customer", customer);
                getServletContext().getRequestDispatcher("/customer/customerEditSuccess.jsp")
                        .forward(request, response);
            }

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.read(Integer.parseInt(request.getParameter("customerId")));

        request.setAttribute("customer", customer);

        getServletContext().getRequestDispatcher("/customer/customerEdit.jsp")
                .forward(request, response);
    }
}
