package pl.coderslab.reports;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import pl.coderslab.Employee;
import pl.coderslab.dao.EmployeeDao;
import pl.coderslab.dao.ReportDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/reports")
public class Reports extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reportType = Integer.parseInt(request.getParameter("reportType"));
        String fromDateString = request.getParameter("fromDate");
        String toDateString = request.getParameter("toDate");

        String errors = "<ul>";

        if (!isValidDate(fromDateString)) {
            errors += "<li>" + " Błędna data od" + "</li>";
        }

        if (!isValidDate(toDateString)) {
            errors += "<li>" + " Błędna data do" + "</li>";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null;
        Date toDate = null;

        try {
            fromDate = format.parse(fromDateString);
            toDate = format.parse(toDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate toDateLocal = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fromDateLocal = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (toDateLocal.isBefore(fromDateLocal)) {
            errors += "<li>" + " Data od nie może być późniejsza niż data do" + "</li>";
        }



        errors += "</ul>";

        if ("<ul></ul>".equals(errors)) {


            //adding 1 day to toDate to generate sql correctly
            toDateLocal = toDateLocal.plusDays(1);

            EmployeeDao employeeDao = new EmployeeDao();
            Employee[] employees = employeeDao.findAll();
            ReportDao reportDao = new ReportDao();

            switch (reportType) {
                case 1:
                    Map<String, Double> reportData1 = new HashMap<>();
                    double totalManHours;
                    for (Employee employee : employees) {
                        totalManHours = round(reportDao.readReport0(employee.getId(), fromDate, toDateLocal), 2);
                        reportData1.put(employee.getFirstName() + " " + employee.getLastName(), totalManHours);
                    }

                    request.setAttribute("reportData", reportData1);
                    request.getRequestDispatcher("/report/report0.jsp").forward(request, response);
                    break;

                case 2:
                    reportDao = new ReportDao();
                    BigDecimal[] reportData2 = reportDao.readReport1(fromDate, toDateLocal);
                    BigDecimal revenue = reportData2[0].setScale(2,RoundingMode.HALF_UP);
                    BigDecimal partsCosts = reportData2[1].setScale(2,RoundingMode.HALF_UP);
                    BigDecimal employeeCost = reportData2[2].setScale(2,RoundingMode.HALF_UP);
                    BigDecimal totalCosts = partsCosts.add(employeeCost);
                    BigDecimal margin = revenue.subtract(totalCosts);

                    request.setAttribute("revenue", revenue);
                    request.setAttribute("partsCosts", partsCosts);
                    request.setAttribute("employeeCost", employeeCost);
                    request.setAttribute("totalCosts", totalCosts);
                    request.setAttribute("margin", margin);
                    request.getRequestDispatcher("/report/report1.jsp").forward(request, response);
                    break;
                case 3:
                    Map<Date, Double> queryResults = reportDao.readReport2b(fromDate, toDateLocal);
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                    if (!(queryResults == null)) {
                        for (Map.Entry<Date, Double> entry : queryResults.entrySet()) {
                            dataset.addValue(entry.getValue(), "", entry.getKey().toString());
                        }
                    }

                    JFreeChart barChart = ChartFactory.createBarChart(
                            "Zamówienia per dzień",
                            "",
                            "Liczba zamówień",
                            dataset,
                            PlotOrientation.VERTICAL,
                            false, true, false);

                    int width = 700;
                    int height = 350;

                    String uploadPathString = getServletContext().getInitParameter("uploadPath");
                    File outputFile = new File(uploadPathString + "/orders.png");
                    ChartUtils.saveChartAsPNG(outputFile, barChart, width, height);

                    File uploads = new File(uploadPathString);
                    File file = File.createTempFile("orders", ".png", uploads);
                    Files.copy(outputFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    request.setAttribute("graphFile", file.toPath().getFileName());
                    request.getRequestDispatcher("/report/report2a.jsp").forward(request, response);
                    break;
            }


        } else {
            request.setAttribute("reportType", reportType);
            request.setAttribute("errors", errors);
            request.setAttribute("fromDate", fromDateString);
            request.setAttribute("toDate", toDateString);
            request.getRequestDispatcher("/report/reports.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("reportType", 1);
        request.getRequestDispatcher("/report/reports.jsp").forward(request, response);

    }

    private boolean isValidDate(String dateString) {
        String regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(dateString);

        return matcher.matches();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
