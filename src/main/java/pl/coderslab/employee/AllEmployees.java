package pl.coderslab.employee;

import pl.coderslab.Employee;
import pl.coderslab.dao.EmployeeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employees")
public class AllEmployees extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee[] employees = employeeDao.findAll();

        request.setAttribute("employees", employees);

        getServletContext().getRequestDispatcher("/employee/employeeAll.jsp")
                .forward(request, response);
    }
}
