package dao;

import java.sql.*;

final class DaoUtil {
    private DaoUtil() {
    }

    // Your schema has no AUTO_INCREMENT, so we pick the next id ourselves.
    // Only ever called with fixed table/column names from our own code.
    static int next(Connection con, String table, String idColumn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + idColumn + "), 0) + 1 FROM " + table;
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    // Writes NULL for a null Integer (setInt can't do that)
    static void setNullableInt(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value == null)
            ps.setNull(index, Types.INTEGER);
        else
            ps.setInt(index, value);
    }
}