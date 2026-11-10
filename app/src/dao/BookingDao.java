package dao;

import db.Database;
import model.Booking;
import model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {
    private final PaymentDao paymentDao = new PaymentDao();

    private static final String SELECT = "SELECT booking_id, customer_id, service_id, payment_id, booking_datetime, status, "
            +
            "charged_price, customer_note, created_at FROM booking";

    private static Booking map(ResultSet rs) throws SQLException {
        return new Booking(rs.getInt("booking_id"), rs.getInt("customer_id"), rs.getInt("service_id"),
                rs.getObject("payment_id", Integer.class), rs.getTimestamp("booking_datetime"),
                rs.getString("status"), rs.getBigDecimal("charged_price"),
                rs.getString("customer_note"), rs.getTimestamp("created_at"));
    }

    public List<Booking> findAll() throws SQLException {
        List<Booking> list = new ArrayList<>();
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT + " ORDER BY booking_datetime DESC");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                list.add(map(rs));
        }
        return list;
    }

    /** Returns null if there is no such booking. */
    public Booking findById(int bookingId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(SELECT + " WHERE booking_id = ?")) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /**
     * Rows for the detail table: id, service title, date/time, status, price,
     * review score.
     */
    public List<Object[]> findByCustomer(int customerId) throws SQLException {
        String sql = "SELECT b.booking_id, s.title, b.booking_datetime, b.status, b.charged_price, r.score " +
                "FROM booking b " +
                "JOIN service s ON s.service_id = b.service_id " +
                "LEFT JOIN review r ON r.booking_id = b.booking_id " +
                "WHERE b.customer_id = ? ORDER BY b.booking_datetime DESC";
        List<Object[]> rows = new ArrayList<>();
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[] { rs.getInt(1), rs.getString(2), rs.getTimestamp(3),
                            rs.getString(4), rs.getBigDecimal(5), rs.getObject(6) });
                }
            }
        }
        return rows;
    }

    public int insert(Booking b) throws SQLException {
        try (Connection con = Database.getConnection()) {
            return insert(con, b);
        }
    }

    int insert(Connection con, Booking b) throws SQLException {
        int id = DaoUtil.next(con, "booking", "booking_id");
        String status = b.getStatus() == null ? "REQUESTED" : b.getStatus();
        String sql = "INSERT INTO booking (booking_id, customer_id, service_id, payment_id, " +
                "booking_datetime, status, charged_price, customer_note) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, b.getCustomerId());
            ps.setInt(3, b.getServiceId());
            DaoUtil.setNullableInt(ps, 4, b.getPaymentId());
            ps.setTimestamp(5, b.getBookingDatetime());
            ps.setString(6, status);
            ps.setBigDecimal(7, b.getChargedPrice());
            ps.setString(8, b.getCustomerNote());
            ps.executeUpdate();
        }
        return id;
    }

    /**
     * Transaction: insert the payment, then the booking that points at it. All or
     * nothing.
     */
    public int createWithPayment(Booking b, Payment p) throws SQLException {
        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);
            try {
                int paymentId = paymentDao.insert(con, p);
                Booking linked = new Booking(0, b.getCustomerId(), b.getServiceId(), paymentId,
                        b.getBookingDatetime(), b.getStatus(), b.getChargedPrice(),
                        b.getCustomerNote(), null);
                int bookingId = insert(con, linked);
                con.commit();
                return bookingId;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    /**
     * Edits the date/time, status, price and note (customer, service and payment
     * stay as they are).
     */
    public void update(Booking b) throws SQLException {
        String sql = "UPDATE booking SET booking_datetime=?, status=?, charged_price=?, " +
                "customer_note=? WHERE booking_id=?";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, b.getBookingDatetime());
            ps.setString(2, b.getStatus());
            ps.setBigDecimal(3, b.getChargedPrice());
            ps.setString(4, b.getCustomerNote());
            ps.setInt(5, b.getId());
            ps.executeUpdate();
        }
    }

    public void updateStatus(int bookingId, String status) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE booking SET status = ? WHERE booking_id = ?")) {
            ps.setString(1, status); // REQUESTED / CONFIRMED / COMPLETED / CANCELLED
            ps.setInt(2, bookingId);
            ps.executeUpdate();
        }
    }

    public void delete(int bookingId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM booking WHERE booking_id = ?")) {
            ps.setInt(1, bookingId);
            ps.executeUpdate(); // its review is deleted too (CASCADE)
        }
    }
}