package pl.coderslab.employee;

import org.apache.commons.io.FilenameUtils;
import pl.coderslab.Employee;
import pl.coderslab.dao.EmployeeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/addemployee")
@MultipartConfig
public class AddEmployee extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String zipCode = request.getParameter("zipCode");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String building = request.getParameter("building");
        String apartment = request.getParameter("apartment");
        String phone = request.getParameter("phone");
        String manHourCostString = request.getParameter("manHourCost");
        String notes = request.getParameter("notes");
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);

        File file = null;
        String avatarFileName = "";
        Employee employee = null;

        String errors = "<ul>";

        if (!"".equals(fileName)) {
            String suffix = "." + FilenameUtils.getExtension(fileName);
            String prefix = "avatar-";
            File uploads = new File(getServletContext().getInitParameter("uploadPath"));
            file = File.createTempFile(prefix, suffix, uploads);
            errors += validateAvatar(filePart, file);
            avatarFileName = getServletContext().getInitParameter("uploadPathToJSP") + file.getName();
        }


        if (!Employee.isValidNumber(manHourCostString)) {
            errors += "<li>" + " Błędny koszt roboczogodziny - nieprawidłowa liczba" + "</li>";
            request.setAttribute("manHourCostString", manHourCostString);

        }

        try {
            Locale.setDefault(new Locale("pl", "PL"));
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            df.setParseBigDecimal(true);
            BigDecimal manHourCost = (BigDecimal) df.parse(manHourCostString);
            employee = new Employee(firstName, lastName, zipCode, city, street, building, apartment, phone, notes, manHourCost, avatarFileName);
        } catch (ParseException e) {
            employee = new Employee(firstName, lastName, zipCode, city, street, building, apartment, phone, notes, avatarFileName);
            request.setAttribute("manHourCostString", manHourCostString);

        }

        errors += Employee.validateEmployee(employee);
        errors += "</ul>";

        if ("<ul></ul>".equals(errors)) {
            EmployeeDao employeeDao = new EmployeeDao();
            Employee employeeCreated = employeeDao.create(employee);

            request.setAttribute("employee", employeeCreated);
            getServletContext().getRequestDispatcher("/employee/employeeAddSuccess.jsp")
                    .forward(request, response);

        } else {

            if (!"".equals(fileName)) {
                file.delete();
            }
            request.setAttribute("employee", employee);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("employee/employeeAdd.jsp").forward(request, response);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/employee/employeeAdd.jsp")
                .forward(request, response);
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return null;
    }

    private String validateAvatar(Part filePart, File file) throws IOException {
        StringBuilder str = new StringBuilder();

        if (filePart.getSize() > 1024 * 1024 * 10) {
            str.append("<li> Zbyt duży plik zdjęcia</li>\n");
        }
        String regexp = ".*(gif|jpe?g|png|bmp)$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(filePart.getSubmittedFileName());

        if (!matcher.matches()) {
            str.append("<li> Niepoprawne rozszerzenie pliku</li>\n");
        }

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (FileNotFoundException e) {
            str.append("<li>Błąd zapisu pliku</li>\n");
        }

        return str.toString();
    }


}
