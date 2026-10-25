package dao;

import db.Database;
import model.Payment;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    public List<Payment> findAll() throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT payment_id, amount_paid, payment_method, payment_status, " +
                "platform_commission, payment_date FROM payment ORDER BY payment_date DESC";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Payment(rs.getInt("payment_id"), rs.getBigDecimal("amount_paid"),
                        rs.getString("payment_method"), rs.getString("payment_status"),
                        rs.getBigDecimal("platform_commission"), rs.getTimestamp("payment_date")));
            }
        }
        return list;
    }

    public int insert(Payment p) throws SQLException {
        try (Connection con = Database.getConnection()) {
            return insert(con, p);
        }
    }

    /** Uses an existing connection so it can take part in a larger transaction. */
    public int insert(Connection con, Payment p) throws SQLException {
        int id = DaoUtil.next(con, "payment", "payment_id");
        String status = p.getPaymentStatus() == null ? "PENDING" : p.getPaymentStatus();
        BigDecimal commission = p.getPlatformCommission() == null ? BigDecimal.ZERO : p.getPlatformCommission();
        String sql = "INSERT INTO payment (payment_id, amount_paid, payment_method, payment_status, " +
                "platform_commission) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setBigDecimal(2, p.getAmountPaid());
            ps.setString(3, p.getPaymentMethod());
            ps.setString(4, status);
            ps.setBigDecimal(5, commission);
            ps.executeUpdate();
        }
        return id;
    }

    public void updateStatus(int paymentId, String status) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE payment SET payment_status = ? WHERE payment_id = ?")) {
            ps.setString(1, status); // PENDING / COMPLETED / FAILED / REFUNDED
            ps.setInt(2, paymentId);
            ps.executeUpdate();
        }
    }

    public void delete(int paymentId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM payment WHERE payment_id = ?")) {
            ps.setInt(1, paymentId);
            ps.executeUpdate(); // bookings pointing at it get payment_id = NULL (SET NULL)
        }
    }
}