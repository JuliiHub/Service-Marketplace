package dao;

import db.Database;
import model.BusinessMerchant;
import model.IndividualMerchant;
import model.Merchant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MerchantDao {
    public List<Merchant> findAll() throws SQLException {
        List<Merchant> list = new ArrayList<>();
        String sql = "SELECT merchant_id, email, display_name, registered_at, verification_status " +
                "FROM merchant ORDER BY display_name";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Merchant(rs.getInt("merchant_id"), rs.getString("email"),
                        rs.getString("display_name"), rs.getTimestamp("registered_at"),
                        rs.getString("verification_status")));
            }
        }
        return list;
    }

    /**
     * The ids inside m and im are ignored; the new merchant id is generated and
     * returned.
     */
    public int insertIndividual(Merchant m, IndividualMerchant im) throws SQLException {
        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);
            try {
                int id = insertMerchantRow(con, m);
                String sql = "INSERT INTO individual_merchant " +
                        "(merchant_id, first_name, last_name, national_id, date_of_birth) VALUES (?,?,?,?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.setString(2, im.getFirstName());
                    ps.setString(3, im.getLastName());
                    ps.setString(4, im.getNationalId());
                    ps.setDate(5, im.getDateOfBirth());
                    ps.executeUpdate();
                }
                con.commit();
                return id;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public int insertBusiness(Merchant m, BusinessMerchant bm) throws SQLException {
        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);
            try {
                int id = insertMerchantRow(con, m);
                String sql = "INSERT INTO business_merchant " +
                        "(merchant_id, legal_name, business_registration_no, tax_number, contact_person) " +
                        "VALUES (?,?,?,?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.setString(2, bm.getLegalName());
                    ps.setString(3, bm.getBusinessRegistrationNo());
                    ps.setString(4, bm.getTaxNumber());
                    ps.setString(5, bm.getContactPerson());
                    ps.executeUpdate();
                }
                con.commit();
                return id;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    private int insertMerchantRow(Connection con, Merchant m) throws SQLException {
        int id = DaoUtil.next(con, "merchant", "merchant_id");
        String status = m.getVerificationStatus() == null ? "PENDING" : m.getVerificationStatus();
        String sql = "INSERT INTO merchant (merchant_id, email, display_name, verification_status) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getDisplayName());
            ps.setString(4, status);
            ps.executeUpdate();
        }
        return id;
    }

    public void updateVerificationStatus(int merchantId, String status) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE merchant SET verification_status = ? WHERE merchant_id = ?")) {
            ps.setString(1, status); // PENDING / VERIFIED / REJECTED (CHECK enforces it)
            ps.setInt(2, merchantId);
            ps.executeUpdate();
        }
    }

    public void delete(int merchantId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM merchant WHERE merchant_id = ?")) {
            ps.setInt(1, merchantId);
            ps.executeUpdate(); // cascades to subtype row and services; fails if those have bookings
        }
    }
}