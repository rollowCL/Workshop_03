package pl.coderslab.dao;

import pl.coderslab.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;

public class OrderDao {

    private static final String CREATE_ORDER_QUERY =
            "INSERT INTO orders(orderDate, plannedRepairDate, problemDesc, repairDesc, statusId, vehicleId, orderNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String READ_ORDER_QUERY =
            "SELECT o.id, o.orderNumber, o.orderDate, o.plannedRepairDate, o.actualRepairStartDate, o.employeeId, o.problemDesc, " +
                    "o.repairDesc, o.statusId, o.vehicleId, o.customerCost, o.partsCost, o.employeeManHourCost, o.manHoursSpent, cancelReasonId, o.actualRepairEndDate " +
                     "FROM orders o WHERE o.id = ?;";

    private static final String UPDATE_ORDER_QUERY =
            "UPDATE orders SET orderDate = ?, plannedRepairDate = ?, actualRepairStartDate = ?, employeeId = ?, problemDesc = ?, " +
                    "repairDesc = ?, statusId = ?, vehicleId = ?, customerCost = ?, partsCost = ?, employeeManHourCost = ?, manHoursSpent = ? WHERE id = ?";

    private static final String DELETE_ORDER_QUERY =
            "DELETE FROM orders WHERE id = ?";

    private static final String PRICE_ORDER_QUERY =
            "UPDATE orders SET customerCost = ?, partsCost = ?, statusId = ?, employeeId = ?, employeeManHourCost = ? WHERE id = ?";

    private static final String START_ORDER_QUERY =
            "UPDATE orders SET statusId = ?, actualRepairStartDate = now() WHERE id = ?";

    private static final String CLOSE_ORDER_QUERY =
            "UPDATE orders SET statusId = ?, manHoursSpent = ?, actualRepairEndDate = now() WHERE id = ?";

    private static final String SUBMIT_ORDER_QUERY =
            "UPDATE orders SET statusId = ? WHERE id = ?";

    private static final String CANCEL_ORDER_QUERY =
            "UPDATE orders SET statusId = ?, cancelReasonId = ? WHERE id = ?";

    private static final String FIND_ALL_ORDERS_QUERY =
            "SELECT o.id, o.orderNumber, o.orderDate, o.plannedRepairDate, o.actualRepairStartDate, o.employeeId, o.problemDesc, " +
                    "o.repairDesc, o.statusId, o.vehicleId, o.customerCost, o.partsCost, o.employeeManHourCost, o.manHoursSpent, cancelReasonId, o.actualRepairEndDate \n" +
                    "FROM orders o ORDER BY o.orderDate;";

    private static final String FIND_ORDERS_BY_STATUS_QUERY =
            "SELECT o.id, o.orderNumber, o.orderDate, o.plannedRepairDate, o.actualRepairStartDate, o.employeeId, o.problemDesc, " +
                    "o.repairDesc, o.statusId, o.vehicleId, o.customerCost, o.partsCost, o.employeeManHourCost, o.manHoursSpent, cancelReasonId, o.actualRepairEndDate \n" +
                    "FROM orders o " +
                    "WHERE o.statusId = ? \n" +
                    "ORDER BY o.orderDate;";

    private static final String FIND_ORDERS_BY_EMPLOYEE_QUERY =
            "SELECT o.id, o.orderNumber, o.orderDate, o.plannedRepairDate, o.actualRepairStartDate, o.employeeId, o.problemDesc, " +
                    "o.repairDesc, o.statusId, o.vehicleId, o.customerCost, o.partsCost, o.employeeManHourCost, o.manHoursSpent, cancelReasonId, o.actualRepairEndDate \n" +
                    "FROM orders o \n" +
                    "WHERE o.employeeId = ? \n" +
                    "ORDER BY o.orderDate;";

    private static final String FIND_ORDERS_BY_CUSTOMER_QUERY =
            "SELECT o.id, o.orderNumber, o.orderDate, o.plannedRepairDate, o.actualRepairStartDate, o.employeeId, o.problemDesc," +
                    " o.repairDesc, o.statusId, o.vehicleId, o.customerCost, o.partsCost, o.employeeManHourCost, o.manHoursSpent, cancelReasonId, o.actualRepairEndDate \n" +
                    "FROM orders o \n" +
                    "JOIN vehicles v on o.vehicleId = v.id "+
                    "WHERE v.customerId = ? \n" +
                    "ORDER BY o.orderDate;";

    private static final String FIND_ORDERS_BY_VEHICLE_QUERY =
            "SELECT o.id, o.orderNumber, o.orderDate, o.plannedRepairDate, o.actualRepairStartDate, o.employeeId, o.problemDesc, " +
                    "o.repairDesc, o.statusId, o.vehicleId, o.customerCost, o.partsCost, o.employeeManHourCost, o.manHoursSpent, cancelReasonId, o.actualRepairEndDate " +
                    "FROM orders o \n" +
                    "WHERE o.vehicleId = ? \n" +
                    "ORDER BY o.orderDate;";

    private static final String FIND_ALL_ORDERS_IN_PROGRESS_QUERY =
            "SELECT o.id, o.orderNumber, o.orderDate, o.plannedRepairDate, o.actualRepairStartDate, o.employeeId, o.problemDesc, " +
                    "o.repairDesc, o.statusId, o.vehicleId, o.customerCost, o.partsCost, o.manHoursSpent, o.employeeManHourCost, cancelReasonId, o.actualRepairEndDate " +
                    "FROM orders o \n" +
                    "WHERE o.statusId = ?\n" +
                    "ORDER BY o.actualRepairStartDate DESC;";


    public Order create(Order order) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {

            java.util.Date orderDate = order.getOrderDate();

            if (orderDate != null) {
                ps.setDate(1, new Date(orderDate.getTime()));
            } else {
                ps.setDate(1, null);
            }

            java.util.Date plannedRepaiDate = order.getPlannedRepairDateStart();

            if (plannedRepaiDate != null) {
                ps.setDate(2, new Date(plannedRepaiDate.getTime()));
            } else {
                ps.setDate(2, null);
            }

            ps.setString(3, order.getProblemDesc());
            ps.setString(4, order.getRepairDesc());
            ps.setInt(5, Config.STATUS_ORDER_OPEN);
            ps.setInt(6, order.getVehicle().getId());
            ps.setString(7, order.getOrderNumber());

            ps.executeUpdate();

            final ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                int id = gk.getInt(1);
                order.setId(id);

                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Order read(int orderId) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_ORDER_QUERY);
        ) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order order = orderDataSelector(rs);

                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void update(Order order) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ORDER_QUERY);) {

            ps.setDate(1, (Date) order.getOrderDate());
            ps.setDate(2, (Date) order.getPlannedRepairDateStart());
            ps.setDate(3, (Date) order.getActualRepairDateStart());
            ps.setString(5, order.getProblemDesc());
            ps.setString(6, order.getRepairDesc());
            ps.setBigDecimal(9, order.getCustomerCost());
            ps.setBigDecimal(10, order.getPartsCost());
            ps.setBigDecimal(11, order.getEmployeeManHourCost());
            ps.setBigDecimal(12, order.getManHoursSpent());
            ps.setInt(13, order.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int orderId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ORDER_QUERY)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void priceOrder(Order order, Employee employee) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(PRICE_ORDER_QUERY);) {

            ps.setBigDecimal(1, order.getCustomerCost());
            ps.setBigDecimal(2, order.getPartsCost());
            ps.setInt(3, Config.STATUS_ORDER_PRICED);
            ps.setInt(4, order.getEmployee().getId());
            ps.setBigDecimal(5, employee.getManHourCost());
            ps.setInt(6, order.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startOrder(int orderId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(START_ORDER_QUERY);) {

            ps.setInt(1, Config.STATUS_ORDER_IN_PROGRESS);
            ps.setInt(2, orderId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeOrder(int orderId, BigDecimal manHoursSpent) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CLOSE_ORDER_QUERY);) {

            ps.setInt(1, Config.STATUS_ORDER_CLOSED);
            ps.setBigDecimal(2, manHoursSpent);
            ps.setInt(3, orderId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void submitOrder(int orderId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SUBMIT_ORDER_QUERY);) {

            ps.setInt(1, Config.STATUS_ORDER_SUBMITTED);
            ps.setInt(2, orderId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(int orderId, int cancelReasonId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CANCEL_ORDER_QUERY);) {

            ps.setInt(1, Config.STATUS_ORDER_CANCELLED);
            ps.setInt(2, cancelReasonId);
            ps.setInt(3, orderId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order[] findAll() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_ORDERS_QUERY);) {
            Order[] orders = new Order[0];
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = orderDataSelector(rs);
                orders = addToArray(order, orders);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order[] findAllByStatus(int statusId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ORDERS_BY_STATUS_QUERY);) {
            Order[] orders = new Order[0];
            ps.setInt(1, statusId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = orderDataSelector(rs);
                orders = addToArray(order, orders);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order[] findAllByVehicle(int vehicleId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ORDERS_BY_VEHICLE_QUERY);) {
            Order[] orders = new Order[0];
            ps.setInt(1, vehicleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = orderDataSelector(rs);
                orders = addToArray(order, orders);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order[] findAllByCustomer(int customerId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ORDERS_BY_CUSTOMER_QUERY);) {
            Order[] orders = new Order[0];
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = orderDataSelector(rs);
                orders = addToArray(order, orders);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order[] findAllByEmployee(int employeeId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ORDERS_BY_EMPLOYEE_QUERY);) {
            Order[] orders = new Order[0];
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = orderDataSelector(rs);
                orders = addToArray(order, orders);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order[] findAllInProgress() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_ORDERS_IN_PROGRESS_QUERY);) {
            Order[] orders = new Order[0];
            ps.setInt(1, Config.STATUS_ORDER_IN_PROGRESS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = orderDataSelector(rs);
                orders = addToArray(order, orders);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Order[] addToArray(Order u, Order[] orders) {
        Order[] tmpExercises = Arrays.copyOf(orders, orders.length + 1);
        tmpExercises[orders.length] = u;
        return tmpExercises;
    }

    private Order orderDataSelector(ResultSet rs) throws SQLException {

        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setOrderNumber(rs.getString("orderNumber"));
        order.setOrderDate(rs.getDate("orderDate"));
        order.setPlannedRepairDateStart(rs.getDate("plannedRepairDate"));
        order.setActualRepairDateStart(rs.getTimestamp("actualRepairStartDate"));
        order.setActualRepairDateEnd(rs.getTimestamp("actualRepairEndDate"));
        order.setProblemDesc(rs.getString("problemDesc"));
        order.setRepairDesc(rs.getString("repairDesc"));
        order.setCustomerCost(rs.getBigDecimal("customerCost"));
        order.setPartsCost(rs.getBigDecimal("partsCost"));
        order.setEmployeeManHourCost(rs.getBigDecimal("employeeManHourCost"));
        order.setManHoursSpent(rs.getBigDecimal("manHoursSpent"));

        StatusDao statusDao = new StatusDao();
        order.setStatus(statusDao.read(rs.getInt("statusId")));

        VehicleDao vehicleDao = new VehicleDao();
        order.setVehicle(vehicleDao.read(rs.getInt("vehicleId")));

        EmployeeDao employeeDao = new EmployeeDao();
        order.setEmployee(employeeDao.read(rs.getInt("employeeId")));

        OrderCancelReasonDao orderCancelReasonDao = new OrderCancelReasonDao();
        order.setOrderCancelReason(orderCancelReasonDao.read(rs.getInt("cancelReasonId")));

        return order;

    }

}
