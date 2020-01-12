package pl.coderslab.orders;

import pl.coderslab.Config;
import pl.coderslab.Order;
import pl.coderslab.Status;
import pl.coderslab.dao.OrderDao;
import pl.coderslab.dao.StatusDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders")
public class AllOrders extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderDao orderDao = new OrderDao();
        Order[] orders = null;

        if (request.getParameter("statusId") == null || request.getParameter("statusId") == "") {

            orders = orderDao.findAll();

        } else {

            orders = orderDao.findAllByStatus(Integer.parseInt(request.getParameter("statusId")));

        }

        StatusDao statusDao = new StatusDao();
        Status[] statuses = statusDao.findAll();

        request.setAttribute("statusId", request.getParameter("statusId"));
        request.setAttribute("orders", orders);
        request.setAttribute("statuses", statuses);
        setOrderStatusConfig(request);

        getServletContext().getRequestDispatcher("/order/orderAll.jsp")
                .forward(request, response);
    }

    public static void setOrderStatusConfig(HttpServletRequest request) {
        request.setAttribute("STATUS_ORDER_OPEN", Config.STATUS_ORDER_OPEN);
        request.setAttribute("STATUS_ORDER_PRICED", Config.STATUS_ORDER_PRICED);
        request.setAttribute("STATUS_ORDER_IN_PROGRESS", Config.STATUS_ORDER_IN_PROGRESS);
        request.setAttribute("STATUS_ORDER_CLOSED", Config.STATUS_ORDER_CLOSED);
        request.setAttribute("STATUS_ORDER_SUBMITTED", Config.STATUS_ORDER_SUBMITTED);
        request.setAttribute("STATUS_ORDER_CANCELLED", Config.STATUS_ORDER_CANCELLED);

    }
}
