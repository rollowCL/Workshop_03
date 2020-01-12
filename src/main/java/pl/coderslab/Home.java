package pl.coderslab;

import pl.coderslab.dao.OrderDao;
import pl.coderslab.orders.AllOrders;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class Home extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderDao orderDao = new OrderDao();
        Order[] orders = orderDao.findAllInProgress();

        request.setAttribute("orders", orders);
        request.setAttribute("statusId", Config.STATUS_ORDER_IN_PROGRESS);

        AllOrders.setOrderStatusConfig(request);

        getServletContext().getRequestDispatcher("/mainView.jsp")
               .forward(request, response);
    }
}
