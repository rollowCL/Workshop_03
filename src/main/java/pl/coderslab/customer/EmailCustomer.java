package pl.coderslab.customer;

import pl.coderslab.EmailUtil;
import pl.coderslab.dao.CustomerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/emailcustomer")
public class EmailCustomer extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        String fromEmail = request.getParameter("fromEmail");
        String password = request.getParameter("password");
        String smtpHost = request.getParameter("smtpHost");
        int customerId = Integer.parseInt(request.getParameter("customerId"));

        String errors = "<ul>";

        if ("".equals(subject)) {
            errors += "<li>" + " Pusty temat maila" + "</li>";
        }

        if ("".equals(message)) {
            errors += "<li>" + " Pusta treść maila" + "</li>";
        }

        if ("".equals(fromEmail)) {
            errors += "<li>" + " Pusta Twój mail" + "</li>";
        }

        if ("".equals(password)) {
            errors += "<li>" + " Puste hasło" + "</li>";
        }

        if ("".equals(smtpHost)) {
            errors += "<li>" + " Pusty SMTP Host" + "</li>";
        }



        errors += "</ul>";

        if ("<ul></ul>".equals(errors)) {
            EmailUtil.createEmail(email, subject, message, fromEmail, password, smtpHost);
            getServletContext().getRequestDispatcher("/customer/customerEmailSuccess.jsp")
                    .forward(request, response);

        } else {
            request.setAttribute("subject", subject);
            request.setAttribute("message", message);
            request.setAttribute("fromEmail", fromEmail);
            request.setAttribute("smtpHost", smtpHost);

            request.setAttribute("errors", errors);

            CustomerDao customerDao = new CustomerDao();
            request.setAttribute("customer", customerDao.read(customerId));
            request.getRequestDispatcher("/customer/customerEmail.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        CustomerDao customerDao = new CustomerDao();
        request.setAttribute("customer", customerDao.read(customerId));

        getServletContext().getRequestDispatcher("/customer/customerEmail.jsp")
                .forward(request, response);
    }
}
