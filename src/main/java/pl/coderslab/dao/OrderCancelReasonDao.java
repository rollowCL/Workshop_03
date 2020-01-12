package pl.coderslab.dao;

import pl.coderslab.OrderCancelReason;

import java.sql.*;
import java.util.Arrays;

public class OrderCancelReasonDao {
    private static final String READ_CANCEL_REASON_QUERY =
            "SELECT id, cancelReasonName FROM ordercancelreasons WHERE id = ?";
    private static final String FIND_ALL_CANCEL_REASONS_QUERY =
            "SELECT id, cancelReasonName FROM ordercancelreasons";

    public OrderCancelReason read(int statusId) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_CANCEL_REASON_QUERY);
        ) {
            ps.setInt(1, statusId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                OrderCancelReason cancelReason = new OrderCancelReason();
                cancelReason.setId(rs.getInt("id"));
                cancelReason.setCancelReasonName(rs.getString("cancelReasonName"));

                return cancelReason;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public OrderCancelReason[] findAll() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_CANCEL_REASONS_QUERY);) {
            OrderCancelReason[] cancelReasons = new OrderCancelReason[0];
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderCancelReason cancelReason = new OrderCancelReason();

                cancelReason.setId(rs.getInt("id"));
                cancelReason.setCancelReasonName(rs.getString("cancelReasonName"));

                cancelReasons = addToArray(cancelReason, cancelReasons);
            }
            return cancelReasons;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private OrderCancelReason[] addToArray(OrderCancelReason u, OrderCancelReason[] cancelReasons) {
        OrderCancelReason[] tmpExercises = Arrays.copyOf(cancelReasons, cancelReasons.length + 1);
        tmpExercises[cancelReasons.length] = u;
        return tmpExercises;
    }

}
