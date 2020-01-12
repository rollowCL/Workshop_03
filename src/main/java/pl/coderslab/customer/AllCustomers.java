package pl.coderslab.customer;

import pl.coderslab.Customer;
import pl.coderslab.dao.CustomerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customers")
public class AllCustomers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao customerDao = new CustomerDao();
        Customer[] customers = customerDao.findAll();

        request.setAttribute("customers", customers);

        getServletContext().getRequestDispatcher("/customer/customerAll.jsp")
                .forward(request, response);
    }
}
