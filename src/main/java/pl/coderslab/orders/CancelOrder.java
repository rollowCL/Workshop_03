package pl.coderslab.orders;

import pl.coderslab.dao.OrderCancelReasonDao;
import pl.coderslab.dao.OrderDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cancelorder")
public class CancelOrder extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int cancelReasonId = Integer.parseInt(request.getParameter("cancelReasonId"));

        OrderDao orderDao = new OrderDao();
        orderDao.cancelOrder(orderId, cancelReasonId);

        response.sendRedirect(request.getContextPath() + "/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDao orderDao = new OrderDao();
        request.setAttribute("order", orderDao.read(orderId));

        OrderCancelReasonDao orderCancelReasonDao = new OrderCancelReasonDao();
        request.setAttribute("cancelReasons", orderCancelReasonDao.findAll());

        getServletContext().getRequestDispatcher("/order/orderCancel.jsp")
                .forward(request, response);
    }
}
