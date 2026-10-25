package dao;

import db.Database;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public List<Customer> findAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT customer_id, email, first_name, last_name, registered_at " +
                "FROM customer ORDER BY last_name, first_name";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Customer(rs.getInt("customer_id"), rs.getString("email"),
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getTimestamp("registered_at")));
            }
        }
        return list;
    }

    // passwordHash is passed separately because the model deliberately doesn't hold
    // it
    public void insert(Customer c, String passwordHash) throws SQLException {
        try (Connection con = Database.getConnection()) {
            int id = DaoUtil.next(con, "customer", "customer_id");
            String sql = "INSERT INTO customer (customer_id, email, password_hash, first_name, last_name) " +
                    "VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, c.getEmail());
                ps.setString(3, passwordHash);
                ps.setString(4, c.getFirstName());
                ps.setString(5, c.getLastName());
                ps.executeUpdate();
            }
        }
    }

    public void update(Customer c) throws SQLException {
        String sql = "UPDATE customer SET email=?, first_name=?, last_name=? WHERE customer_id=?";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getEmail());
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int customerId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM customer WHERE customer_id = ?")) {
            ps.setInt(1, customerId);
            ps.executeUpdate(); // fails if the customer has bookings (RESTRICT)
        }
    }
}