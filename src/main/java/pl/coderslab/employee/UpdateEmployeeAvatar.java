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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/updateavatar")
@MultipartConfig
public class UpdateEmployeeAvatar extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String employeeIdString = request.getParameter("employeeId");
        int employeeId = Integer.parseInt(employeeIdString);
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        File file = null;
        String avatarFileName = "";
        String errors = "<ul>";

        if (!"".equals(fileName)) {
            String suffix = "." + FilenameUtils.getExtension(fileName);
            String prefix = "avatar-";
            File uploads = new File(getServletContext().getInitParameter("uploadPath"));
            file = File.createTempFile(prefix, suffix, uploads);
            errors += validateAvatar(filePart, file);
            avatarFileName = getServletContext().getInitParameter("uploadPathToJSP") + file.getName();
        }

        errors += "</ul>";

        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.read(employeeId);

        if ("<ul></ul>".equals(errors)) {

            employeeDao.updateAvatar(avatarFileName, employeeId);

            request.setAttribute("employee", employee);
            getServletContext().getRequestDispatcher("/employee/employeeDetails.jsp")
                    .forward(request, response);

        } else {

            if (!"".equals(fileName)) {
                file.delete();
            }
            request.setAttribute("employee", employee);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("employee/employeeAvatar.jsp").forward(request, response);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.read(Integer.parseInt(request.getParameter("employeeId")));

        request.setAttribute("employee", employee);

        getServletContext().getRequestDispatcher("/employee/employeeAvatar.jsp")
                .forward(request, response);
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

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return null;
    }

}
