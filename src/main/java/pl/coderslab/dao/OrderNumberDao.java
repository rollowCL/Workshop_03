package pl.coderslab.dao;

import pl.coderslab.Order;
import pl.coderslab.OrderNumber;

import java.sql.*;
import java.util.Arrays;

public class OrderNumberDao {
    private static final String READ_MAX_NUMBER_QUERY =
            "select MAX(o.number) as number\n" +
                    "from ordernumbers o\n" +
                    "WHERE year = ?;";

    private static final String CREATE_NEW_NUMBER_QUERY =
            "INSERT INTO ordernumbers(year, number) VALUES (?, ?)";


    public OrderNumber create(OrderNumber orderNumber) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_NEW_NUMBER_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setInt(1, orderNumber.getYear());
            ps.setInt(2, orderNumber.getNumber());
            ps.executeUpdate();

            final ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                int id = gk.getInt(1);
                orderNumber.setId(id);

                return orderNumber;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int readMax(int year) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_MAX_NUMBER_QUERY);
        ) {
            ps.setInt(1, year);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
