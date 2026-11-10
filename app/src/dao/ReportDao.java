package dao;

import db.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    /**
     * T1: one merchant's services. Columns: service_id, title, review count,
     * average score.
     */
    public List<Object[]> servicesWithAvgScore(int merchantId) throws SQLException {
        return run("SELECT s.service_id, s.title, COUNT(r.review_id), ROUND(AVG(r.score), 2) " +
                "FROM service s " +
                "LEFT JOIN booking b ON b.service_id = s.service_id " +
                "LEFT JOIN review r ON r.booking_id = b.booking_id " +
                "WHERE s.merchant_id = ? " +
                "GROUP BY s.service_id, s.title ORDER BY s.title", merchantId);
    }

    /** T2: platform commission per month. Columns: month, total commission. */
    public List<Object[]> commissionPerMonth() throws SQLException {
        return run("SELECT DATE_FORMAT(payment_date, '%Y-%m') AS pay_month, SUM(platform_commission) " +
                "FROM payment WHERE payment_status = 'COMPLETED' " +
                "GROUP BY DATE_FORMAT(payment_date, '%Y-%m') ORDER BY pay_month");
    }

    /** T3: customers who booked from MORE THAN moreThan different merchants. */
    public List<Object[]> customersWithManyMerchants(int moreThan) throws SQLException {
        return run("SELECT c.customer_id, c.first_name, c.last_name, COUNT(DISTINCT s.merchant_id) " +
                "FROM customer c " +
                "JOIN booking b ON b.customer_id = c.customer_id " +
                "JOIN service s ON s.service_id = b.service_id " +
                "GROUP BY c.customer_id, c.first_name, c.last_name " +
                "HAVING COUNT(DISTINCT s.merchant_id) > ? ORDER BY 4 DESC", moreThan);
    }

    /** T4: merchants with no booking in the last `days` days. */
    public List<Object[]> merchantsWithoutRecentBookings(int days) throws SQLException {
        return run("SELECT m.merchant_id, m.display_name FROM merchant m " +
                "WHERE NOT EXISTS (SELECT 1 FROM service s " +
                "JOIN booking b ON b.service_id = s.service_id " +
                "WHERE s.merchant_id = m.merchant_id " +
                "AND b.booking_datetime >= DATE_SUB(CURRENT_DATE, INTERVAL ? DAY)) " +
                "ORDER BY m.merchant_id", days);
    }

    /** T5: the category with the highest average booking value. */
    public List<Object[]> topCategoryByAvgBooking() throws SQLException {
        return run("SELECT c.category_id, c.category_name, ROUND(AVG(b.charged_price), 2) AS avg_value " +
                "FROM category c " +
                "JOIN service s ON s.category_id = c.category_id " +
                "JOIN booking b ON b.service_id = s.service_id " +
                "GROUP BY c.category_id, c.category_name " +
                "ORDER BY avg_value DESC LIMIT 1");
    }

    private List<Object[]> run(String sql, Object... params) throws SQLException {
        List<Object[]> rows = new ArrayList<>();
        try (Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++)
                ps.setObject(i + 1, params[i]);
            try (ResultSet rs = ps.executeQuery()) {
                int cols = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    Object[] row = new Object[cols];
                    for (int i = 0; i < cols; i++)
                        row[i] = rs.getObject(i + 1);
                    rows.add(row);
                }
            }
        }
        return rows;
    }
}