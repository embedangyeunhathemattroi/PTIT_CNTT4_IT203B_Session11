import java.sql.Connection;
import java.sql.DriverManager;

public class B1_Hospital_DB {

    // Hằng số cấu hình
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // Hàm lấy connection
    // public static Connection getConnection() throws Exception {
    // return DriverManager.getConnection(URL, USER, PASSWORD);
    // }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}