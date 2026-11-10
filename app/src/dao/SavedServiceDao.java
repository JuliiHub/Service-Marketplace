package dao;

import db.Database;
import model.CustomerSavedService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SavedServiceDao {
    public List<CustomerSavedService> findByCustomer(int customerId) throws SQLException {
        List<CustomerSavedService> list = new ArrayList<>();
        String sql = "SELECT customer_id, service_id, saved_at FROM customer_saved_service " +
                "WHERE customer_id = ? ORDER BY saved_at DESC";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new CustomerSavedService(rs.getInt(1), rs.getInt(2), rs.getTimestamp(3)));
                }
            }
        }
        return list;
    }

    /**
     * Rows for a "saved services" table: service_id, title, list_price, saved_at.
     */
    public List<Object[]> findDetailsByCustomer(int customerId) throws SQLException {
        String sql = "SELECT s.service_id, s.title, s.list_price, cs.saved_at " +
                "FROM customer_saved_service cs " +
                "JOIN service s ON s.service_id = cs.service_id " +
                "WHERE cs.customer_id = ? ORDER BY cs.saved_at DESC";
        List<Object[]> rows = new ArrayList<>();
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[] { rs.getInt(1), rs.getString(2),
                            rs.getBigDecimal(3), rs.getTimestamp(4) });
                }
            }
        }
        return rows;
    }

    public void save(int customerId, int serviceId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO customer_saved_service (customer_id, service_id) VALUES (?,?)")) {
            ps.setInt(1, customerId);
            ps.setInt(2, serviceId);
            ps.executeUpdate(); // saving the same service twice violates the composite PK
        }
    }

    public void remove(int customerId, int serviceId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM customer_saved_service WHERE customer_id = ? AND service_id = ?")) {
            ps.setInt(1, customerId);
            ps.setInt(2, serviceId);
            ps.executeUpdate();
        }
    }
}