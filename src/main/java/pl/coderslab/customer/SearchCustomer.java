package pl.coderslab.customer;

import pl.coderslab.Customer;
import pl.coderslab.dao.CustomerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/searchcustomer")
public class SearchCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastnamepattern = request.getParameter("lastnamepattern");

        CustomerDao customerDao = new CustomerDao();
        Customer[] customers = customerDao.findAllByName(lastnamepattern);

        request.setAttribute("lastnamepattern", lastnamepattern);
        request.setAttribute("customers", customers);

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/customer/customerSearch.jsp")
                .forward(request, response);
    }
}
