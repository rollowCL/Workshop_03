package pl.coderslab.orders;

import pl.coderslab.dao.OrderDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/submitorder")
public class SubmitOrder extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        OrderDao orderDao = new OrderDao();
        orderDao.submitOrder(orderId);

        response.sendRedirect(request.getContextPath() + "/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDao orderDao = new OrderDao();
        request.setAttribute("order", orderDao.read(orderId));

        getServletContext().getRequestDispatcher("/order/orderSubmit.jsp")
                .forward(request, response);
    }
}
