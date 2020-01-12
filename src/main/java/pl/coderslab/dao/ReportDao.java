package pl.coderslab.dao;

import org.jfree.data.jdbc.JDBCCategoryDataset;
import pl.coderslab.Config;
import pl.coderslab.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportDao {

    private static final String REPORT_0_QUERY =
            "SELECT o.id, o.employeeId, o.actualRepairStartDate, IF(o.actualRepairEndDate is null, NOW(), o.actualRepairEndDate) as repairEndDate\n" +
                    "       FROM orders o\n" +
                    "WHERE o.statusId <> 7\n" +
                    "AND o.employeeId = ?\n" +
                    " AND o.actualRepairStartDate >= ?\n" +
                    "AND IF(o.actualRepairEndDate is null, NOW(), o.actualRepairEndDate) <= ?;\n";

    private static final String REPORT_1_QUERY =
           "SELECT sum(o.customerCost) AS REVENUE, sum(o.partsCost) AS PARTS_COST,\n" +
                   "       sum(o.employeeManHourCost * o.manHoursSpent) AS EMPLOYEE_COST\n" +
                   "FROM orders o\n" +
                   "WHERE o.statusId between ? and ?\n" +
                   "  AND o.actualRepairStartDate >= ?\n" +
                   "  AND o.actualRepairEndDate <= ?;";

    private static final String REPORT_2_QUERY =
            "SELECT o.orderDate, COUNT(*) AS COUNT " +
                    "FROM orders o " +
                    " WHERE o.orderDate >= ?\n" +
                    " AND o.orderDate < ? \n" +
                    " GROUP BY o.orderDate \n" +
                    " ORDER BY o.orderDate;";


    public double readReport0(int employeeId, java.util.Date fromDate, LocalDate toDateLocal) {

        Date toDate = Date.valueOf(toDateLocal);

        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(REPORT_0_QUERY);
        ) {

            ps.setInt(1, employeeId);
            ps.setDate(2, new Date(fromDate.getTime()));
            ps.setDate(3, new Date(toDate.getTime()));

            ResultSet rs = ps.executeQuery();
            double totalManHours = 0;

            while (rs.next()) {
                LocalDateTime orderFromDate = rs.getTimestamp("actualRepairStartDate").toLocalDateTime();
                LocalDateTime orderToDate = rs.getTimestamp("repairEndDate").toLocalDateTime();
                totalManHours +=
                        Order.calculateManHours(orderFromDate, orderToDate, Config.WORK_HOURS_START, Config.WORK_MINUTES_START
                                , Config.WORK_HOURS_END, Config.WORK_MINUTES_END);

            }

            return totalManHours;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public BigDecimal[] readReport1(java.util.Date fromDate, LocalDate toDateLocal) {

        Date toDate = Date.valueOf(toDateLocal);

        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(REPORT_1_QUERY);
        ) {

            ps.setInt(1, Config.STATUS_ORDER_IN_PROGRESS);
            ps.setInt(2, Config.STATUS_ORDER_SUBMITTED);
            ps.setDate(3, new Date(fromDate.getTime()));
            ps.setDate(4, new Date(toDate.getTime()));

            ResultSet rs = ps.executeQuery();
            BigDecimal[] resultValues = new BigDecimal[3];

            if (rs.next()) {
                resultValues[0] = rs.getBigDecimal("REVENUE");
                resultValues[1] = rs.getBigDecimal("PARTS_COST");
                resultValues[2] = rs.getBigDecimal("EMPLOYEE_COST");
            }

            return resultValues;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public JDBCCategoryDataset readReport2a() {

        try (Connection conn = DbUtil.getConnection();

        ) {
            JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn);
            dataset.executeQuery(REPORT_2_QUERY);
            return dataset;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<java.util.Date, Double> readReport2b(java.util.Date fromDate, LocalDate toDateLocal) {
        Date toDate = Date.valueOf(toDateLocal);

        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(REPORT_2_QUERY);
        ) {
            ps.setDate(1, new Date(fromDate.getTime()));
            ps.setDate(2, new Date(toDate.getTime()));

            ResultSet rs = ps.executeQuery();
            LinkedHashMap<java.util.Date, Double> resultData = new LinkedHashMap<>();

            while (rs.next()) {
                resultData.put(rs.getDate("orderDate"),rs.getDouble("COUNT"));
            }
            return resultData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
