package dao;

import db.Database;
import model.Review;

import java.sql.*;

public class ReviewDao {
    /**
     * Returns the review for a booking, or null if there isn't one (max one per
     * booking).
     */
    public Review findByBooking(int bookingId) throws SQLException {
        String sql = "SELECT review_id, booking_id, score, comment, created_at " +
                "FROM review WHERE booking_id = ?";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return null;
                return new Review(rs.getInt("review_id"), rs.getInt("booking_id"),
                        rs.getInt("score"), rs.getString("comment"), rs.getTimestamp("created_at"));
            }
        }
    }

    public int insert(Review r) throws SQLException {
        try (Connection con = Database.getConnection()) {
            int id = DaoUtil.next(con, "review", "review_id");
            String sql = "INSERT INTO review (review_id, booking_id, score, comment) VALUES (?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, r.getBookingId());
                ps.setInt(3, r.getScore()); // CHECK allows 1 to 5 only
                ps.setString(4, r.getComment());
                ps.executeUpdate();
            }
            return id;
        }
    }

    public void update(Review r) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE review SET score = ?, comment = ? WHERE review_id = ?")) {
            ps.setInt(1, r.getScore());
            ps.setString(2, r.getComment());
            ps.setInt(3, r.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int reviewId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM review WHERE review_id = ?")) {
            ps.setInt(1, reviewId);
            ps.executeUpdate();
        }
    }
}