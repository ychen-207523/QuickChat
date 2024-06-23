import java.sql.Connection;
import java.sql.DriverManager;

public class SimpleDbTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/quickchat";
        String user = "dev";
        String password = "dev@123";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database!");
                conn.close();
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
