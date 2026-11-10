import dao.ReportDao;
import dao.ServiceDao;
import db.Database;

import java.sql.Connection;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        try (Connection con = Database.getConnection()) {
            System.out.println("Connected to: " + con.getCatalog());
        }

        System.out.println("Services: " + new ServiceDao().findAll().size());

        ReportDao rd = new ReportDao();
        System.out.println("-- Merchant 101: services and average score");
        for (Object[] r : rd.servicesWithAvgScore(101))
            System.out.println(Arrays.toString(r));

        System.out.println("-- Commission per month");
        for (Object[] r : rd.commissionPerMonth())
            System.out.println(Arrays.toString(r));

        System.out.println("-- Category with the highest average booking value");
        for (Object[] r : rd.topCategoryByAvgBooking())
            System.out.println(Arrays.toString(r));
    }
}