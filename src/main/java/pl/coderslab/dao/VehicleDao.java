package pl.coderslab.dao;

import pl.coderslab.Vehicle;

import java.sql.*;
import java.util.Arrays;

public class VehicleDao {

    private static final String CREATE_VEHICLE_QUERY =
            "INSERT INTO vehicles(model, manufacturer, productionYear, registrationNo, nextTechnicalCheckDate, customerId) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String READ_VEHICLE_QUERY =
            "SELECT v.id, v.model, v.manufacturer, v.productionYear, v.registrationNo, v.nextTechnicalCheckDate, v.customerId \n" +
                     "FROM vehicles v \n" +
                    "WHERE v.id = ?\n";

    private static final String UPDATE_VEHICLE_QUERY =
            "UPDATE vehicles SET model = ?, manufacturer = ?, productionYear = ?, registrationNo = ?, nextTechnicalCheckDate = ?, " +
                    "customerId = ? WHERE id = ?";

    private static final String DELETE_VEHICLE_QUERY =
            "DELETE FROM vehicles WHERE id = ?";

    private static final String FIND_ALL_VEHICLES_QUERY =
            "SELECT v.id, v.model, v.manufacturer, v.productionYear, v.registrationNo, v.nextTechnicalCheckDate, v.customerId \n" +
                    "FROM vehicles v \n";

    private static final String FIND_VEHICLES_BY_CUSTOMER_QUERY =
            "SELECT v.id, v.model, v.manufacturer, v.productionYear, v.registrationNo, v.nextTechnicalCheckDate, v.customerId \n" +
                    "FROM vehicles v \n" +
                    "WHERE v.customerId = ?\n";

    private static final String FIND_VEHICLES_BY_REGNO_QUERY =
            "SELECT v.id, v.model, v.manufacturer, v.productionYear, v.registrationNo, v.nextTechnicalCheckDate, v.customerId \n" +
                    "FROM vehicles v \n" +
                    "WHERE v.registrationNo = ?\n";

    public Vehicle create(Vehicle vehicle) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, vehicle.getModel());
            ps.setString(2, vehicle.getManufacturer());
            ps.setInt(3, vehicle.getProductionYear());
            ps.setString(4, vehicle.getRegistrationNo());

            java.util.Date nextTechnicalCheckDate = vehicle.getNextTechnicalCheckDate();

            if (nextTechnicalCheckDate != null) {
                ps.setDate(5, new Date(nextTechnicalCheckDate.getTime()));
            } else {
                ps.setDate(5, null);
            }


            ps.setInt(6, vehicle.getCustomer().getId());

            ps.executeUpdate();

            final ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                int id = gk.getInt(1);
                vehicle.setId(id);

                return vehicle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vehicle read(int vehicleId) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_VEHICLE_QUERY);
        ) {
            ps.setInt(1, vehicleId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setManufacturer(rs.getString("manufacturer"));
                vehicle.setProductionYear(rs.getInt("productionYear"));
                vehicle.setRegistrationNo(rs.getString("registrationNo"));
                vehicle.setNextTechnicalCheckDate(rs.getDate("nextTechnicalCheckDate"));

                CustomerDao customerDao = new CustomerDao();
                vehicle.setCustomer(customerDao.read(rs.getInt("customerId")));

                return vehicle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void update(Vehicle vehicle) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_VEHICLE_QUERY);) {

            ps.setString(1, vehicle.getModel());
            ps.setString(2, vehicle.getManufacturer());
            ps.setInt(3, vehicle.getProductionYear());
            ps.setString(4, vehicle.getRegistrationNo());

            java.util.Date nextTechnicalCheckDate = vehicle.getNextTechnicalCheckDate();

            if (nextTechnicalCheckDate != null) {
                ps.setDate(5, new Date(nextTechnicalCheckDate.getTime()));
            } else {
                ps.setDate(5, null);
            }
            ps.setInt(6, vehicle.getCustomer().getId());
            ps.setInt(7, vehicle.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int vehicleId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_VEHICLE_QUERY)) {
            ps.setInt(1, vehicleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vehicle[] findAll() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_VEHICLES_QUERY);) {
            Vehicle[] vehicles = new Vehicle[0];
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setManufacturer(rs.getString("manufacturer"));
                vehicle.setProductionYear(rs.getInt("productionYear"));
                vehicle.setRegistrationNo(rs.getString("registrationNo"));
                vehicle.setNextTechnicalCheckDate(rs.getDate("nextTechnicalCheckDate"));

                CustomerDao customerDao = new CustomerDao();
                vehicle.setCustomer(customerDao.read(rs.getInt("customerId")));


                vehicles = addToArray(vehicle, vehicles);
            }
            return vehicles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Vehicle[] findVehicleByRegNo(String registrationNo) {

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_VEHICLES_BY_REGNO_QUERY);) {
            ps.setString(1, registrationNo);
            ResultSet rs = ps.executeQuery();
            Vehicle[] vehicles = new Vehicle[0];

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setManufacturer(rs.getString("manufacturer"));
                vehicle.setProductionYear(rs.getInt("productionYear"));
                vehicle.setRegistrationNo(rs.getString("registrationNo"));
                vehicle.setNextTechnicalCheckDate(rs.getDate("nextTechnicalCheckDate"));

                CustomerDao customerDao = new CustomerDao();
                vehicle.setCustomer(customerDao.read(rs.getInt("customerId")));


                vehicles = addToArray(vehicle, vehicles);

            }
            return vehicles;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean vehicleExists(String registrationNo) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(FIND_VEHICLES_BY_REGNO_QUERY);
        ) {
            ps.setString(1, registrationNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public Vehicle[] findAllByCustomer(int customerId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_VEHICLES_BY_CUSTOMER_QUERY);) {
            Vehicle[] vehicles = new Vehicle[0];

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setManufacturer(rs.getString("manufacturer"));
                vehicle.setProductionYear(rs.getInt("productionYear"));
                vehicle.setRegistrationNo(rs.getString("registrationNo"));
                vehicle.setNextTechnicalCheckDate(rs.getDate("nextTechnicalCheckDate"));

                CustomerDao customerDao = new CustomerDao();
                vehicle.setCustomer(customerDao.read(rs.getInt("customerId")));


                vehicles = addToArray(vehicle, vehicles);
            }
            return vehicles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Vehicle[] addToArray(Vehicle u, Vehicle[] vehicles) {
        Vehicle[] tmpExercises = Arrays.copyOf(vehicles, vehicles.length + 1);
        tmpExercises[vehicles.length] = u;
        return tmpExercises;
    }

}
