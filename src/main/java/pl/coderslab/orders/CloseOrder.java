package pl.coderslab.orders;

import pl.coderslab.dao.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/closeorder")
public class CloseOrder extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        BigDecimal manHoursSpent = BigDecimal.valueOf(Double.parseDouble(request.getParameter("manHoursSpent")));

        OrderDao orderDao = new OrderDao();
        orderDao.closeOrder(orderId, manHoursSpent);

        response.sendRedirect(request.getContextPath() + "/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDao orderDao = new OrderDao();
        request.setAttribute("order", orderDao.read(orderId));

        getServletContext().getRequestDispatcher("/order/orderClose.jsp")
                .forward(request, response);
    }
}
