package pl.coderslab.dao;

import pl.coderslab.Customer;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class CustomerDao {
    private static final String CREATE_CUSTOMER_QUERY =
            "INSERT INTO customers(firstName, lastName, birthDate, email) VALUES (?, ?, ?, ?)";
    private static final String READ_CUSTOMER_QUERY =
            "SELECT id, firstName, lastName, birthDate, email FROM customers WHERE id = ?";
    private static final String UPDATE_CUSTOMER_QUERY =
            "UPDATE customers SET firstName = ?, lastName = ?, birthDate = ?, email = ? WHERE id = ?";
    private static final String DELETE_CUSTOMER_QUERY =
            "DELETE FROM customers WHERE id = ?";
    private static final String FIND_ALL_CUSTOMERS_QUERY =
            "SELECT id, firstName, lastName, birthDate, email  FROM customers";
    private static final String FIND_CUSTOMERS_BY_NAME_QUERY =
            "SELECT id, firstName, lastName, birthDate, email  FROM customers WHERE lastName LIKE ?";
    private static final String FIND_CUSTOMERS_BY_EMAIL_QUERY =
            "SELECT id, firstName, lastName, birthDate, email  FROM customers WHERE email = ? AND email IS NOT NULL";


    public Customer create(Customer customer) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());

            String birthDateString = customer.getBirthDate();
            java.util.Date birthdate = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birthdate = sdf.parse(birthDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (birthdate != null) {
                ps.setDate(3, new Date(birthdate.getTime()));
            } else {
                ps.setDate(3, null);
            }
            ps.setString(4, customer.getEmail());

            ps.executeUpdate();

            final ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                int id = gk.getInt(1);
                customer.setId(id);

                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Customer read(int customerId) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(READ_CUSTOMER_QUERY);
        ) {
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setBirthDate("" + rs.getDate("birthDate"));
                customer.setEmail((rs.getString("email")));

                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void update(Customer customer) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_CUSTOMER_QUERY);) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());

            String birthDateString = customer.getBirthDate();
            java.util.Date birthdate = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birthdate = sdf.parse(birthDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (birthdate != null) {
                ps.setDate(3, new Date(birthdate.getTime()));
            } else {
                ps.setDate(3, null);
            }

            ps.setString(4, customer.getEmail());
            ps.setInt(5, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int customerId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_CUSTOMER_QUERY)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer[] findAll() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_CUSTOMERS_QUERY);) {
            Customer[] customers = new Customer[0];
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();

                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setBirthDate("" + rs.getDate("birthDate"));
                customer.setEmail((rs.getString("email")));

                customers = addToArray(customer, customers);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer[] findAllByName(String namePattern) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_CUSTOMERS_BY_NAME_QUERY);) {
            Customer[] customers = new Customer[0];

            ps.setString(1, "%" + namePattern + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();

                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setBirthDate("" + rs.getDate("birthDate"));
                customer.setEmail((rs.getString("email")));

                customers = addToArray(customer, customers);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int customerExists(String email) {
        try (Connection conn = DbUtil.getConnection();
             final PreparedStatement ps = conn.prepareStatement(FIND_CUSTOMERS_BY_EMAIL_QUERY);
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); //return id of existing customer
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; //return 0 if user with the email does not exists

    }


    private Customer[] addToArray(Customer u, Customer[] customers) {
        Customer[] tmpExercises = Arrays.copyOf(customers, customers.length + 1);
        tmpExercises[customers.length] = u;
        return tmpExercises;
    }


}



