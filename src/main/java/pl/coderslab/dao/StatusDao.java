package pl.coderslab.dao;

import pl.coderslab.Customer;
import pl.coderslab.Order;
import pl.coderslab.Status;

import java.sql.*;
import java.util.Arrays;

public class StatusDao {
    private static final String CREATE_STATUS_QUERY =
            "INSERT INTO statuses(statusName) VALUES (?)";
    private static final String READ_STATUS_QUERY =
            "SELECT id, statusName FROM statuses WHERE id = ?";
    private static final String FIND_ALL_STATUSES_QUERY =
            "SELECT id, statusName FROM statuses";
    private static final String UPDATE_STATUS_QUERY =
            "UPDATE statuses SET statusName = ? WHERE id = ?";
    private static final String DELETE_STATUS_QUERY =
            "DELETE FROM statuses WHERE id = ?";
    private static final String READ_MIN_STATUS_ID =
            "SELECT MIN(id) AS Min FROM statuses;";

    public Status create(Status status) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_STATUS_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, status.getStatusName());

            ps.executeUpdate();

            final ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                int id = gk.getInt(1);
                status.setId(id);

                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Status read(int statusId) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_STATUS_QUERY);
        ) {
            ps.setInt(1, statusId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Status status = new Status();
                status.setId(rs.getInt("id"));
                status.setStatusName(rs.getString("statusName"));

                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void update(Status status) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_STATUS_QUERY);) {

            statement.setString(1, status.getStatusName());
            statement.setInt(2, status.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int statusId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_STATUS_QUERY)) {
            statement.setInt(1, statusId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Status[] findAll() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_STATUSES_QUERY);) {
            Status[] statuses = new Status[0];
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Status status = new Status();

                status.setId(rs.getInt("id"));
                status.setStatusName(rs.getString("statusName"));

                statuses = addToArray(status, statuses);
            }
            return statuses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Status[] addToArray(Status u, Status[] statuses) {
        Status[] tmpExercises = Arrays.copyOf(statuses, statuses.length + 1);
        tmpExercises[statuses.length] = u;
        return tmpExercises;
    }

    public int readMinStatusId() {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_MIN_STATUS_ID);
        ) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                return rs.getInt("Min");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
