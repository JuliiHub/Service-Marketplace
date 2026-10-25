import db.Database;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {
        try (Connection con = Database.getConnection()) {
            System.out.println("Connected to: " + con.getCatalog());
        }
    }
}