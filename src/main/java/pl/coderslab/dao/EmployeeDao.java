package pl.coderslab.dao;

import pl.coderslab.Employee;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;

public class EmployeeDao {

    private static final String CREATE_EMPLOYEE_QUERY =
            "INSERT INTO employees(firstName, lastName, zipCode, city, street, building, apartment, phone, notes, manHourCost, " +
                    "avatarFileName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_EMPLOYEE_QUERY =
            "SELECT id, firstName, lastName, zipCode, city, street, building, apartment, phone, notes, manHourCost, " +
                    "avatarFileName FROM employees WHERE id = ?";
    private static final String UPDATE_EMPLOYEE_QUERY =
            "UPDATE employees SET firstName = ?, lastName = ?, zipCode = ?, city = ?, street = ?, building = ?, " +
                    "apartment = ?, phone = ?, notes = ?, manHourCost = ? WHERE id = ?";
    private static final String DELETE_EMPLOYEE_QUERY =
            "DELETE FROM employees WHERE id = ?";
    private static final String FIND_ALL_EMPLOYEES_QUERY =
            "SELECT id, firstName, lastName, zipCode, city, street, building, apartment, phone, notes, manHourCost, " +
                    "avatarFileName FROM employees";
    private static final String FIND_EMPLOYEES_BY_NAME_QUERY =
            "SELECT id, firstName, lastName, zipCode, city, street, building, apartment, phone, notes, manHourCost, " +
                    "avatarFileName FROM employees WHERE lastName LIKE ?";
    private static final String UPDATE_AVATAR_QUERY =
            "UPDATE employees SET avatarFileName = ? WHERE id = ?";


    public Employee create(Employee employee) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_EMPLOYEE_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getZipCode());
            ps.setString(4, employee.getCity());
            ps.setString(5, employee.getStreet());
            ps.setString(6, employee.getBuilding());
            ps.setString(7, employee.getApartment());
            ps.setString(8, employee.getPhone());
            ps.setString(9, employee.getNotes());
            ps.setBigDecimal(10, employee.getManHourCost());
            ps.setString(11, employee.getAvatarFileName());

            ps.executeUpdate();

            final ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                int id = gk.getInt(1);
                employee.setId(id);

                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Employee read(int employeeId) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_EMPLOYEE_QUERY);
        ) {
            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setZipCode(rs.getString("zipCode"));
                employee.setCity(rs.getString("city"));
                employee.setStreet(rs.getString("street"));
                employee.setBuilding(rs.getString("building"));
                employee.setApartment(rs.getString("apartment"));
                employee.setPhone(rs.getString("phone"));
                employee.setNotes(rs.getString("notes"));
                employee.setManHourCost(rs.getBigDecimal("manHourCost"));
                employee.setAvatarFileName(rs.getString("avatarFileName"));

                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void update(Employee employee) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EMPLOYEE_QUERY);) {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getZipCode());
            ps.setString(4, employee.getCity());
            ps.setString(5, employee.getStreet());
            ps.setString(6, employee.getBuilding());
            ps.setString(7, employee.getApartment());
            ps.setString(8, employee.getPhone());
            ps.setString(9, employee.getNotes());
            ps.setBigDecimal(10, employee.getManHourCost());
            ps.setInt(11, employee.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int employeeId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_EMPLOYEE_QUERY)) {
            ps.setInt(1, employeeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee[] findAll() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_EMPLOYEES_QUERY);) {
            Employee[] employees = new Employee[0];
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setZipCode(rs.getString("zipCode"));
                employee.setCity(rs.getString("city"));
                employee.setStreet(rs.getString("street"));
                employee.setBuilding(rs.getString("building"));
                employee.setApartment(rs.getString("apartment"));
                employee.setPhone(rs.getString("phone"));
                employee.setNotes(rs.getString("notes"));
                employee.setManHourCost(rs.getBigDecimal("manHourCost"));
                employee.setAvatarFileName(rs.getString("avatarFileName"));

                employees = addToArray(employee, employees);
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee[] findAllByName(String namePattern) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_EMPLOYEES_BY_NAME_QUERY);) {
            Employee[] employees = new Employee[0];

            ps.setString(1, "*" + namePattern + "*");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setZipCode(rs.getString("zipCode"));
                employee.setCity(rs.getString("city"));
                employee.setStreet(rs.getString("street"));
                employee.setBuilding(rs.getString("building"));
                employee.setApartment(rs.getString("apartment"));
                employee.setPhone(rs.getString("phone"));
                employee.setNotes(rs.getString("notes"));
                employee.setManHourCost(rs.getBigDecimal("manHourCost"));
                employee.setAvatarFileName(rs.getString("avatarFileName"));

                employees = addToArray(employee, employees);
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateAvatar(String avatarFileName, int id) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_AVATAR_QUERY);) {

            ps.setString(1, avatarFileName);
            ps.setInt(2, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Employee[] addToArray(Employee u, Employee[] employees) {
        Employee[] tmpExercises = Arrays.copyOf(employees, employees.length + 1);
        tmpExercises[employees.length] = u;
        return tmpExercises;
    }

}
