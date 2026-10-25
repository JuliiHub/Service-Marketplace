package dao;

import db.Database;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public List<Category> findAll() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, category_name, is_active, parent_id " +
                "FROM category ORDER BY category_name";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("category_name"),
                        rs.getBoolean("is_active"), rs.getObject("parent_id", Integer.class)));
            }
        }
        return list;
    }

    public void insert(Category c) throws SQLException {
        try (Connection con = Database.getConnection()) {
            int id = DaoUtil.next(con, "category", "category_id");
            String sql = "INSERT INTO category (category_id, category_name, is_active, parent_id) " +
                    "VALUES (?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, c.getName());
                ps.setBoolean(3, c.isActive());
                DaoUtil.setNullableInt(ps, 4, c.getParentId());
                ps.executeUpdate();
            }
        }
    }

    public void update(Category c) throws SQLException {
        String sql = "UPDATE category SET category_name=?, is_active=?, parent_id=? WHERE category_id=?";
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setBoolean(2, c.isActive());
            DaoUtil.setNullableInt(ps, 3, c.getParentId());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int categoryId) throws SQLException {
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM category WHERE category_id = ?")) {
            ps.setInt(1, categoryId);
            ps.executeUpdate(); // fails if services use it (RESTRICT)
        }
    }
}