package pl.coderslab.employee;

import pl.coderslab.Employee;
import pl.coderslab.dao.EmployeeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employeedetails")
public class EmployeeDetails extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.read(Integer.parseInt(request.getParameter("employeeId")));

        request.setAttribute("employee", employee);

        getServletContext().getRequestDispatcher("/employee/employeeDetails.jsp")
                .forward(request, response);
    }
}
