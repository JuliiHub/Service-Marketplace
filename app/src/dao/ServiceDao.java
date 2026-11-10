package dao;

import db.Database;
import model.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {
    private static final String JOINED = "SELECT s.service_id, s.title, c.category_name, m.display_name, s.list_price, "
            +
            "s.duration, s.is_offered " +
            "FROM service s " +
            "JOIN category c ON c.category_id = s.category_id " +
            "JOIN merchant m ON m.merchant_id = s.merchant_id ";

    /**
     * Rows for the browse table: id, title, category, merchant, price, duration,
     * offered.
     */
    public List<Object[]> findAll() throws SQLException {
        return query(JOINED + "ORDER BY s.title", new ArrayList<>());
    }

    /** Any argument may be null/blank to skip that filter. */
    public List<Object[]> search(String keyword, Integer categoryId, BigDecimal maxPrice)
            throws SQLException {
        StringBuilder sql = new StringBuilder(JOINED + "WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.isBlank()) {
            sql.append("AND s.title LIKE ? ");
            params.add("%" + keyword.trim() + "%");
        }
        if (categoryId != null) {
            sql.append("AND s.category_id = ? ");
            params.add(categoryId);
        }
        if (maxPrice != null) {
            sql.append("AND s.list_price <= ? ");
            params.add(maxPrice);
        }
        sql.append("ORDER BY s.title");
        return query(sql.toString(), params);
    }

    private List<Object[]> query(String sql, List<Object> params) throws SQLException {
        List<Object[]> rows = new ArrayList<>();
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++)
                ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getBigDecimal(5), rs.getInt(6), rs.getBoolean(7) });
                }
            }
        }
        return rows;
    }

    /** Returns null if there is no such service. */
    public Service findById(int serviceId) throws SQLException {
        String sql = "SELECT service_id, merchant_id, category_id, title, description, " +
                "list_price, duration, is_offered FROM service WHERE service_id = ?";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return null;
                return new Service(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getBigDecimal(6), rs.getInt(7), rs.getBoolean(8));
            }
        }
    }

    /** The id inside s is ignored; the new id is generated and returned. */
    public int insert(Service s) throws SQLException {
        try (Connection con = Database.getConnection()) {
            int id = DaoUtil.next(con, "service", "service_id");
            String sql = "INSERT INTO service (service_id, merchant_id, category_id, title, " +
                    "description, list_price, duration, is_offered) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, s.getMerchantId());
                ps.setInt(3, s.getCategoryId());
                ps.setString(4, s.getTitle());
                ps.setString(5, s.getDescription());
                ps.setBigDecimal(6, s.getListPrice()); // CHECK: >= 0
                ps.setInt(7, s.getDuration()); // CHECK: > 0
                ps.setBoolean(8, s.isOffered());
                ps.executeUpdate();
            }
            return id;
        }
    }

    public void update(Service s) throws SQLException {
        String sql = "UPDATE service SET category_id=?, title=?, description=?, list_price=?, " +
                "duration=?, is_offered=? WHERE service_id=?";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getCategoryId());
            ps.setString(2, s.getTitle());
            ps.setString(3, s.getDescription());
            ps.setBigDecimal(4, s.getListPrice());
            ps.setInt(5, s.getDuration());
            ps.setBoolean(6, s.isOffered());
            ps.setInt(7, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int serviceId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM service WHERE service_id = ?")) {
            ps.setInt(1, serviceId);
            ps.executeUpdate(); // fails if the service has bookings (RESTRICT)
        }
    }
}