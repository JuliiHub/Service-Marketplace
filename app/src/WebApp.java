import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dao.*;
import model.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Local test website for the database. No libraries: just the JDK's HttpServer
 * and the existing DAOs. Run it, then open http://localhost:8080
 */
public class WebApp {
    static final ServiceDao services = new ServiceDao();
    static final CategoryDao categories = new CategoryDao();
    static final MerchantDao merchants = new MerchantDao();
    static final CustomerDao customers = new CustomerDao();
    static final BookingDao bookings = new BookingDao();
    static final PaymentDao payments = new PaymentDao();
    static final ReviewDao reviews = new ReviewDao();
    static final ReportDao reports = new ReportDao();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.createContext("/", WebApp::handle);
        server.start();
        System.out.println("Open http://localhost:8080");
        if (!Files.exists(Path.of("web/style.css")) && !Files.exists(Path.of("style.css")))
            System.out.println("WARNING: web/style.css not found, so the site will be unstyled. Start the app from the app folder.");
    }

    // ---------------------------------------------------------------- routing

    static void handle(HttpExchange ex) throws IOException {
        try {
            Map<String, String> p = params(ex);
            switch (ex.getRequestURI().getPath()) {
                case "/" -> send(ex, 200, homePage());
                case "/style.css" -> css(ex);

                case "/services" -> send(ex, 200, servicesPage(p));
                case "/service/add" -> {
                    services.insert(new Service(0, num(p, "merchant"), num(p, "category"), p.get("title"),
                            p.get("description"), new BigDecimal(p.get("price").trim()), num(p, "duration"),
                            p.containsKey("offered")));
                    redirect(ex, "/services", "Service added.");
                }
                case "/service/delete" -> {
                    services.delete(num(p, "id"));
                    redirect(ex, "/services", "Service deleted.");
                }

                case "/customers" -> send(ex, 200, customersPage(p));
                case "/customer/add" -> {
                    customers.insert(new Customer(0, p.get("email"), p.get("first"), p.get("last"), null),
                            hash(p.get("password")));
                    redirect(ex, "/customers", "Customer added.");
                }
                case "/customer/delete" -> {
                    customers.delete(num(p, "id"));
                    redirect(ex, "/customers", "Customer deleted.");
                }

                case "/bookings" -> send(ex, 200, bookingsPage(p));
                case "/booking" -> send(ex, 200, bookingPage(p));
                case "/booking/new" -> send(ex, 200, newBookingPage(p));
                case "/booking/create" -> {
                    String w = p.get("when").replace('T', ' ');
                    if (w.length() == 16)
                        w += ":00";
                    Booking b = new Booking(0, num(p, "customer"), num(p, "service"), null, Timestamp.valueOf(w),
                            "CONFIRMED", new BigDecimal(p.get("price").trim()), p.get("note"), null);
                    Payment pay = new Payment(0, new BigDecimal(p.get("amount").trim()), p.get("method"),
                            "COMPLETED", new BigDecimal(p.get("commission").trim()), null);
                    int id = bookings.createWithPayment(b, pay);
                    redirect(ex, "/booking?id=" + id, "Booking and payment created.");
                }
                case "/booking/status" -> {
                    bookings.updateStatus(num(p, "id"), p.get("status"));
                    redirect(ex, "/booking?id=" + num(p, "id"), "Status updated.");
                }
                case "/review/save" -> {
                    int bid = num(p, "id");
                    Review old = reviews.findByBooking(bid);
                    int score = num(p, "score");
                    if (old == null)
                        reviews.insert(new Review(0, bid, score, p.get("comment"), null));
                    else
                        reviews.update(new Review(old.getId(), bid, score, p.get("comment"), null));
                    redirect(ex, "/booking?id=" + bid, "Review saved.");
                }
                case "/review/delete" -> {
                    int bid = num(p, "id");
                    Review old = reviews.findByBooking(bid);
                    if (old != null)
                        reviews.delete(old.getId());
                    redirect(ex, "/booking?id=" + bid, "Review deleted.");
                }

                case "/reports" -> send(ex, 200, reportsPage(p));
                default -> send(ex, 404, page("Not found", "<p class=\"msg\">No such page.</p>"));
            }
        } catch (SQLException e) {
            send(ex, 400, page("Database error", "<h1>Database error</h1><p class=\"msg\">" + esc(explain(e))
                    + "</p><p><a href=\"javascript:history.back()\">Go back</a></p>"));
        } catch (RuntimeException e) {
            send(ex, 400, page("Invalid input", "<h1>Invalid input</h1><p class=\"msg\">"
                    + esc(String.valueOf(e.getMessage()))
                    + "</p><p><a href=\"javascript:history.back()\">Go back</a></p>"));
        }
    }

    // ------------------------------------------------------------------ pages

    static String homePage() {
        String[][] cards = {
                { "&#128270;", "Services", "Browse, search, add and delete services.", "/services" },
                { "&#128100;", "Customers", "View and manage customer accounts.", "/customers" },
                { "&#128197;", "Bookings", "A customer's bookings with payments and reviews.", "/bookings" },
                { "+", "New booking", "Create a booking and its payment together.", "/booking/new" },
                { "&#128202;", "Reports", "The five aggregate reports.", "/reports" } };
        StringBuilder sb = new StringBuilder("<div class=\"dashboard-grid\">");
        for (String[] c : cards)
            sb.append("<a class=\"dashboard-card\" href=\"").append(c[3]).append("\"><div class=\"dashboard-icon\">")
                    .append(c[0]).append("</div><div><h2>").append(c[1]).append("</h2><p>").append(c[2])
                    .append("</p></div></a>");
        return page("Home", header("Service Marketplace", "Local test site for the database.", "")
                + sb.append("</div>"));
    }

    static String servicesPage(Map<String, String> p) throws SQLException {
        String q = p.getOrDefault("q", ""), cat = p.getOrDefault("cat", ""), max = p.getOrDefault("max", "");
        List<Object[]> rows = services.search(q,
                cat.isBlank() ? null : Integer.valueOf(cat.trim()),
                max.isBlank() ? null : new BigDecimal(max.trim()));
        String filter = "<form method=\"get\" action=\"/services\"><div class=\"search-grid\">"
                + field("Service title", "<input name=\"q\" placeholder=\"e.g. massage\" value=\"" + esc(q) + "\">")
                + field("Category", select("cat", categoryOpts(true), cat))
                + field("Maximum price (VND)",
                        "<input name=\"max\" type=\"number\" min=\"0\" step=\"1000\" value=\"" + esc(max) + "\">")
                + "</div><div class=\"form-actions\"><button>Search</button>"
                + "<a class=\"secondary-link\" href=\"/services\">Clear</a></div></form>";
        String add = "<form method=\"post\" action=\"/service/add\"><div class=\"form-grid\">"
                + field("Merchant", select("merchant", merchantOpts(), ""))
                + field("Category", select("category", categoryOpts(false), "")) + "</div>"
                + "<div class=\"full-field\"><label>Service title</label><input name=\"title\" required></div>"
                + "<div class=\"full-field\"><label>Description</label>"
                + "<textarea name=\"description\" rows=\"3\"></textarea></div>"
                + "<div class=\"service-meta-grid\">"
                + field("List price (VND)", "<input name=\"price\" type=\"number\" min=\"0\" step=\"1000\" required>")
                + field("Duration (minutes)", "<input name=\"duration\" type=\"number\" min=\"1\" required>")
                + "<div class=\"checkbox-field\"><label>Availability</label><label class=\"checkbox-card\">"
                + "<input type=\"checkbox\" name=\"offered\" checked><span>Currently offered</span></label></div></div>"
                + "<div class=\"management-actions\"><div class=\"primary-actions\">"
                + "<button>Add service</button></div></div></form>";
        return page("Services", msg(p) + header("Services", "Browse, search, add and delete services.", "")
                + section("Search filters", "Leave a field empty to skip it.", filter)
                + section("All services", "",
                        table(new String[] { "ID", "Service", "Category", "Merchant", "Price", "Minutes", "Offered" },
                                rows, r -> post("/service/delete", "Delete", "id", r[0].toString())))
                + section("Add a service", "Saved straight into the database.", add));
    }

    static String customersPage(Map<String, String> p) throws SQLException {
        List<Object[]> rows = new ArrayList<>();
        for (Customer c : customers.findAll())
            rows.add(new Object[] { c.getId(), c.getFirstName() + " " + c.getLastName(), c.getEmail(),
                    c.getRegisteredAt() });
        String add = "<form method=\"post\" action=\"/customer/add\"><div class=\"form-grid\">"
                + field("First name", "<input name=\"first\" required>")
                + field("Last name", "<input name=\"last\" required>")
                + field("Email", "<input name=\"email\" type=\"email\" required>")
                + field("Password", "<input name=\"password\" type=\"password\" required>")
                + "</div><div class=\"management-actions\"><div class=\"primary-actions\">"
                + "<button>Add customer</button></div></div></form>";
        return page("Customers", msg(p) + header("Customers", "View and manage customer accounts.", "")
                + section("All customers", "",
                        table(new String[] { "ID", "Name", "Email", "Registered" }, rows,
                                r -> "<a class=\"table-action\" href=\"/bookings?customer=" + r[0] + "\">Bookings</a> "
                                        + post("/customer/delete", "Delete", "id", r[0].toString())))
                + section("Add a customer", "The password is stored as a salted hash.", add));
    }

    static String bookingsPage(Map<String, String> p) throws SQLException {
        List<String[]> opts = customerOpts();
        String sel = p.getOrDefault("customer", opts.isEmpty() ? "" : opts.get(0)[0]);
        StringBuilder sb = new StringBuilder(msg(p)
                + header("Customer bookings", "Pick a customer to see their booking history.",
                        "<a class=\"action-button\" href=\"/booking/new\">+ New booking</a>"));
        sb.append(section("Customer", "",
                "<form method=\"get\" action=\"/bookings\"><div class=\"customer-selector\"><div><label>Customer</label>"
                        + select("customer", opts, sel) + "</div><button>Show bookings</button></div></form>"));
        if (!sel.isBlank()) {
            int cid = Integer.parseInt(sel.trim());
            Customer c = customers.findById(cid);
            if (c != null)
                sb.append("<section><div class=\"info-grid\">" + infoItem("Customer", esc("#" + c.getId() + " " + c))
                        + infoItem("Email", esc(String.valueOf(c.getEmail())))
                        + infoItem("Registered", esc(String.valueOf(c.getRegisteredAt()))) + "</div></section>");
            sb.append(section("Booking history", "",
                    table(new String[] { "Booking", "Service", "Date and time", "Status", "Price", "Review" },
                            bookings.findByCustomer(cid),
                            r -> "<a class=\"table-action\" href=\"/booking?id=" + r[0] + "\">View</a>")));
        }
        return page("Bookings", sb.toString());
    }

    static String bookingPage(Map<String, String> p) throws SQLException {
        Booking b = bookings.findById(num(p, "id"));
        if (b == null)
            return page("Booking", "<p class=\"msg\">No such booking.</p>");
        Service s = services.findById(b.getServiceId());
        Customer c = customers.findById(b.getCustomerId());
        Payment pay = null;
        if (b.getPaymentId() != null)
            for (Payment x : payments.findAll())
                if (x.getId() == b.getPaymentId())
                    pay = x;
        Review r = reviews.findByBooking(b.getId());
        String id = String.valueOf(b.getId());

        List<String[]> statuses = new ArrayList<>();
        for (String st : new String[] { "REQUESTED", "CONFIRMED", "COMPLETED", "CANCELLED" })
            statuses.add(new String[] { st, st });
        String note = b.getCustomerNote() == null || b.getCustomerNote().isBlank() ? ""
                : "<div class=\"booking-note\"><span class=\"info-label\">Customer note</span><p>"
                        + esc(b.getCustomerNote()) + "</p></div>";
        String booking = "<div class=\"info-grid booking-info-grid\">"
                + infoItem("Customer", esc(c == null ? "?" : "#" + c.getId() + " " + c))
                + infoItem("Service", esc(s == null ? "?" : s.getTitle()))
                + infoItem("Date and time", esc(String.valueOf(b.getBookingDatetime())))
                + infoItem("Charged price", money(b.getChargedPrice()))
                + infoItem("Status", badge(b.getStatus())) + "</div>" + note
                + "<form method=\"post\" action=\"/booking/status\"><input type=\"hidden\" name=\"id\" value=\"" + id
                + "\"><div class=\"status-update\"><div><label>Change status</label>"
                + select("status", statuses, b.getStatus()) + "</div><button>Update status</button></div></form>";
        String payment = pay == null
                ? "<p class=\"table-note\">No payment recorded for this booking.</p>"
                : "<div class=\"info-grid\">" + infoItem("Payment ID", "#" + pay.getId())
                        + infoItem("Method", esc(pay.getPaymentMethod()))
                        + infoItem("Amount paid", money(pay.getAmountPaid()))
                        + infoItem("Platform commission", money(pay.getPlatformCommission()))
                        + infoItem("Status", badge(pay.getPaymentStatus()))
                        + infoItem("Paid on", esc(String.valueOf(pay.getPaymentDate()))) + "</div>";
        String review = "<form method=\"post\" action=\"/review/save\"><input type=\"hidden\" name=\"id\" value=\""
                + id + "\"><div class=\"review-grid\">"
                + field("Score (1 to 5)", "<input name=\"score\" type=\"number\" min=\"1\" max=\"5\" required value=\""
                        + (r == null ? "" : String.valueOf(r.getScore())) + "\">")
                + field("Comment", "<textarea name=\"comment\" rows=\"3\">"
                        + (r == null || r.getComment() == null ? "" : esc(r.getComment())) + "</textarea>")
                + "</div><div class=\"form-actions\"><button>Save review</button></div></form>"
                + (r == null ? "" : post("/review/delete", "Delete review", "id", id));
        return page("Booking " + id, msg(p)
                + header("Booking #" + id, "Booking information, payment and review.",
                        "<a class=\"secondary-link\" href=\"/bookings?customer=" + b.getCustomerId()
                                + "\">&#8592; Back to bookings</a>")
                + section("Booking information", "", booking)
                + section("Payment details", "The payment linked to this booking.", payment)
                + section("Customer review", "A booking can have at most one review.", review));
    }

    static String newBookingPage(Map<String, String> p) throws SQLException {
        List<String[]> svc = new ArrayList<>();
        for (Object[] r : services.findAll())
            svc.add(new String[] { r[0].toString(), r[0] + " - " + r[1] + " (" + r[4] + ")" });
        List<String[]> methods = new ArrayList<>();
        for (String m : new String[] { "MOMO", "VNPay", "BANK_TRANSFER", "CREDIT_CARD", "CASH" })
            methods.add(new String[] { m, m });
        String booking = "<div class=\"form-grid\">" + field("Customer", select("customer", customerOpts(), ""))
                + field("Service", select("service", svc, ""))
                + field("Date and time", "<input name=\"when\" type=\"datetime-local\" required>")
                + field("Charged price (VND)", "<input name=\"price\" type=\"number\" min=\"0\" step=\"1000\" required>")
                + "</div><div class=\"full-field\"><label>Customer note</label>"
                + "<textarea name=\"note\" rows=\"3\"></textarea></div>";
        String payment = "<div class=\"payment-grid\">" + field("Payment method", select("method", methods, "MOMO"))
                + field("Amount paid (VND)", "<input name=\"amount\" type=\"number\" min=\"0\" step=\"1000\" required>")
                + field("Platform commission (VND)",
                        "<input name=\"commission\" type=\"number\" min=\"0\" step=\"1000\" value=\"0\" required>")
                + "</div>";
        return page("New booking", msg(p)
                + header("New booking", "Create a booking and its payment together in one transaction.", "")
                + "<form method=\"post\" action=\"/booking/create\">"
                + section("Booking information", "Customer, service and schedule.", booking)
                + section("Payment information", "Payment details for this booking.", payment)
                + "<div class=\"transaction-info\"><div class=\"transaction-icon\">&#10003;</div><div>"
                + "<strong>Atomic transaction</strong><p>The booking and payment are created together. "
                + "If either fails, neither is saved.</p></div></div>"
                + "<div class=\"create-actions\"><button>Create booking and payment</button>"
                + "<button type=\"reset\">Clear form</button></div></form>");
    }

    static String reportsPage(Map<String, String> p) throws SQLException {
        List<String[]> mopts = merchantOpts();
        String m = p.getOrDefault("merchant", mopts.isEmpty() ? "0" : mopts.get(0)[0]);
        String n = p.getOrDefault("n", "3"), days = p.getOrDefault("days", "60");
        String filter = "<form method=\"get\" action=\"/reports\"><div class=\"report-filter\">"
                + field("Merchant (report 1)", select("merchant", mopts, m))
                + field("More than (merchants, report 3)",
                        "<input name=\"n\" type=\"number\" min=\"0\" value=\"" + esc(n) + "\">")
                + field("Days without bookings (report 4)",
                        "<input name=\"days\" type=\"number\" min=\"1\" value=\"" + esc(days) + "\">")
                + "<button>Run reports</button></div></form>";
        return page("Reports", header("Reports", "The five aggregate reports.", "")
                + section("Inputs", "Change the values and run again.", filter)
                + reportCard(1, "Service review performance", "One merchant's services with their average score.",
                        table(new String[] { "Service ID", "Service", "Reviews", "Average score" },
                                reports.servicesWithAvgScore(Integer.parseInt(m.trim())), null))
                + reportCard(2, "Platform commission by month", "Total commission from completed payments.",
                        table(new String[] { "Month", "Total commission" }, reports.commissionPerMonth(), null))
                + reportCard(3, "Customers across multiple merchants", "Customers who booked from more than N merchants.",
                        table(new String[] { "Customer ID", "First name", "Last name", "Different merchants" },
                                reports.customersWithManyMerchants(Integer.parseInt(n.trim())), null))
                + reportCard(4, "Inactive merchants", "Merchants with no booking in the last N days.",
                        table(new String[] { "Merchant ID", "Merchant" },
                                reports.merchantsWithoutRecentBookings(Integer.parseInt(days.trim())), null))
                + reportCard(5, "Top category by booking value", "The category with the highest average booking value.",
                        table(new String[] { "Category ID", "Category", "Average booking value" },
                                reports.topCategoryByAvgBooking(), null)));
    }

    // ---------------------------------------------------------------- helpers

    static String page(String title, String body) {
        return "<!DOCTYPE html><html lang=\"vi\"><head><meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><title>" + esc(title)
                + "</title><link rel=\"stylesheet\" href=\"/style.css\"></head><body><nav>"
                + "<a href=\"/\">Home</a><a href=\"/services\">Services</a><a href=\"/customers\">Customers</a>"
                + "<a href=\"/bookings\">Bookings</a><a href=\"/booking/new\">New booking</a>"
                + "<a href=\"/reports\">Reports</a></nav><main>" + body + "</main></body></html>";
    }

    static String header(String title, String sub, String right) {
        return "<div class=\"page-header\"><div><h1>" + esc(title) + "</h1><p class=\"muted\">" + esc(sub)
                + "</p></div>" + right + "</div>";
    }

    static String section(String title, String sub, String inner) {
        return "<section><div class=\"section-header\"><div><h2>" + esc(title) + "</h2>"
                + (sub.isEmpty() ? "" : "<p class=\"muted\">" + esc(sub) + "</p>") + "</div></div>" + inner
                + "</section>";
    }

    static String reportCard(int n, String title, String desc, String content) {
        return "<section class=\"report-card\"><div class=\"report-heading\"><div class=\"report-number\">" + n
                + "</div><div><h2>" + esc(title) + "</h2><p class=\"muted\">" + esc(desc) + "</p></div></div>"
                + content + "</section>";
    }

    static String field(String label, String control) {
        return "<div><label>" + esc(label) + "</label>" + control + "</div>";
    }

    static String infoItem(String label, String htmlValue) {
        return "<div class=\"info-item\"><span class=\"info-label\">" + esc(label) + "</span><strong>" + htmlValue
                + "</strong></div>";
    }

    static String badge(String s) {
        return "<span class=\"status status-" + esc(s.toLowerCase()) + "\">" + esc(s) + "</span>";
    }

    static String money(BigDecimal v) {
        return v == null ? "" : String.format("%,.0f VND", v);
    }

    static String table(String[] heads, List<Object[]> rows, Function<Object[], String> extra) {
        StringBuilder sb = new StringBuilder(
                "<div class=\"table-wrapper\"><table class=\"service-table\"><thead><tr>");
        for (String h : heads)
            sb.append("<th>").append(esc(h)).append("</th>");
        if (extra != null)
            sb.append("<th>Action</th>");
        sb.append("</tr></thead><tbody>");
        for (Object[] r : rows) {
            sb.append("<tr>");
            for (int i = 0; i < r.length; i++)
                sb.append(cell(heads[i], r[i]));
            if (extra != null)
                sb.append("<td>").append(extra.apply(r)).append("</td>");
            sb.append("</tr>");
        }
        if (rows.isEmpty())
            sb.append("<tr><td colspan=\"").append(heads.length + (extra != null ? 1 : 0))
                    .append("\" class=\"empty-result\">No rows found.</td></tr>");
        return sb.append("</tbody></table></div><p class=\"table-note\">").append(rows.size())
                .append(" row(s)</p>").toString();
    }

    /** Formats one table cell depending on its column. */
    static String cell(String head, Object v) {
        if (v == null)
            return "<td></td>";
        String h = head.toLowerCase(), s = v.toString();
        if (h.equals("offered"))
            return "<td><span class=\"status " + (Boolean.TRUE.equals(v) ? "status-active\">Yes" : "status-inactive\">No")
                    + "</span></td>";
        if (h.equals("status"))
            return "<td>" + badge(s) + "</td>";
        if (v instanceof Number n && (h.contains("price") || h.contains("commission") || h.contains("value")))
            return "<td class=\"price-cell\">" + String.format("%,.0f", n.doubleValue()) + " VND</td>";
        if (h.equals("review") || h.equals("average score"))
            return "<td><span class=\"rating\">&#9733; " + esc(s) + "</span></td>";
        if (h.equals("id") || h.equals("booking") || h.endsWith(" id"))
            return "<td class=\"id-cell\">#" + esc(s) + "</td>";
        return "<td>" + esc(s) + "</td>";
    }

    static String select(String name, List<String[]> opts, String selected) {
        StringBuilder sb = new StringBuilder("<select name=\"" + name + "\">");
        for (String[] o : opts)
            sb.append("<option value=\"").append(esc(o[0])).append("\"")
                    .append(o[0].equals(selected) ? " selected" : "").append(">").append(esc(o[1]))
                    .append("</option>");
        return sb.append("</select>").toString();
    }

    /** A small POST form with one button, asking for confirmation first. */
    static String post(String action, String label, String... kv) {
        StringBuilder sb = new StringBuilder("<form method=\"post\" action=\"" + action
                + "\" style=\"display:inline\">");
        for (int i = 0; i < kv.length; i += 2)
            sb.append("<input type=\"hidden\" name=\"").append(kv[i]).append("\" value=\"").append(esc(kv[i + 1]))
                    .append("\">");
        return sb.append("<button type=\"submit\" class=\"danger-button\" onclick=\"return confirm('Are you sure?')\">")
                .append(label).append("</button></form>").toString();
    }

    /** Green notice shown after a successful action. */
    static String msg(Map<String, String> p) {
        return p.containsKey("msg")
                ? "<p class=\"msg\" style=\"background:#f0fdf4;color:#166534;border-color:#bbf7d0\">"
                        + esc(p.get("msg")) + "</p>"
                : "";
    }

    static List<String[]> merchantOpts() throws SQLException {
        List<String[]> o = new ArrayList<>();
        for (Merchant m : merchants.findAll())
            o.add(new String[] { String.valueOf(m.getId()), m.getId() + " - " + m.getDisplayName() });
        return o;
    }

    static List<String[]> categoryOpts(boolean any) throws SQLException {
        List<String[]> o = new ArrayList<>();
        if (any)
            o.add(new String[] { "", "Any category" });
        for (Category c : categories.findAll())
            o.add(new String[] { String.valueOf(c.getId()), c.getName() });
        return o;
    }

    static List<String[]> customerOpts() throws SQLException {
        List<String[]> o = new ArrayList<>();
        for (Customer c : customers.findAll())
            o.add(new String[] { String.valueOf(c.getId()), c.getId() + " - " + c });
        return o;
    }

    static int num(Map<String, String> p, String key) {
        return Integer.parseInt(p.get(key).trim());
    }

    static String esc(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    /** Turns common MySQL error codes into plain language. */
    static String explain(SQLException e) {
        String text = switch (e.getErrorCode()) {
            case 1062 -> "That value already exists (a duplicate key or a UNIQUE field such as an email).";
            case 1452 -> "A record it points to does not exist (foreign key).";
            case 1451 -> "Other records still use this one, so it cannot be deleted.";
            case 3819 -> "A value breaks a CHECK rule (for example a negative price or a score outside 1 to 5).";
            case 1406 -> "A value is too long for its column.";
            default -> e.getMessage();
        };
        return text + " (MySQL error " + e.getErrorCode() + ")";
    }

    static String hash(String password) {
        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            byte[] h = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(new PBEKeySpec(password.toCharArray(), salt, 100_000, 256)).getEncoded();
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(h);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------------------------------- http plumbing

    static Map<String, String> params(HttpExchange ex) throws IOException {
        Map<String, String> m = new HashMap<>();
        String query = ex.getRequestURI().getRawQuery();
        if (query != null)
            parse(query, m);
        String body = new String(ex.getRequestBody().readAllBytes(), UTF_8);
        if (!body.isEmpty())
            parse(body, m);
        return m;
    }

    static void parse(String s, Map<String, String> m) {
        for (String kv : s.split("&")) {
            if (kv.isEmpty())
                continue;
            int i = kv.indexOf('=');
            String k = i < 0 ? kv : kv.substring(0, i);
            String v = i < 0 ? "" : kv.substring(i + 1);
            m.put(URLDecoder.decode(k, UTF_8), URLDecoder.decode(v, UTF_8));
        }
    }

    static void send(HttpExchange ex, int code, String html) throws IOException {
        byte[] b = html.getBytes(UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        ex.sendResponseHeaders(code, b.length);
        try (var o = ex.getResponseBody()) {
            o.write(b);
        }
    }

    static void redirect(HttpExchange ex, String to, String message) throws IOException {
        ex.getResponseHeaders().set("Location",
                to + (to.contains("?") ? "&" : "?") + "msg=" + URLEncoder.encode(message, UTF_8));
        ex.sendResponseHeaders(303, -1);
        ex.close();
    }

    /** Serves web/style.css (or style.css) from the folder you run the app in. */
    static void css(HttpExchange ex) throws IOException {
        for (String f : new String[] { "web/style.css", "style.css", "app/web/style.css" }) {
            Path path = Path.of(f);
            if (Files.exists(path)) {
                byte[] b = Files.readAllBytes(path);
                ex.getResponseHeaders().set("Content-Type", "text/css; charset=UTF-8");
                ex.sendResponseHeaders(200, b.length);
                try (var o = ex.getResponseBody()) {
                    o.write(b);
                }
                return;
            }
        }
        ex.sendResponseHeaders(404, -1);
        ex.close();
    }
}
