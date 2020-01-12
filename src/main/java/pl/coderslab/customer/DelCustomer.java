package pl.coderslab.customer;

import pl.coderslab.Customer;
import pl.coderslab.dao.CustomerDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delcustomer")
public class DelCustomer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));

        CustomerDao customerDao = new CustomerDao();
        customerDao.delete(customerId);

        request.setAttribute("deleted", 1);

        getServletContext().getRequestDispatcher("/customer/customerDel.jsp")
                .forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.read(Integer.parseInt(request.getParameter("customerId")));

        request.setAttribute("customer", customer);

        getServletContext().getRequestDispatcher("/customer/customerDel.jsp")
                .forward(request, response);
    }
}
