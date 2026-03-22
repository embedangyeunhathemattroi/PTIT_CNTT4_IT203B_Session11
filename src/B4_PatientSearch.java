import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
// Khi nối chuỗi trực tiếp, hacker nhập `' OR '1'='1` → câu SQL thành:
// ```sql: WHERE name = '' OR '1'='1'
//  `'1'='1'` luôn đúng → điều kiện WHERE luôn TRUE → hệ thống trả về **toàn bộ dữ liệu bệnh nhân** (bị lộ).

public class B4_PatientSearch {

    public void findPatient(String input) {

        String url = "jdbc:mysql://localhost:3306/test_jdbc";
        String user = "root";
        String password = "123456";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            input = input.replace("'", "");
            input = input.replace("--", "");
            input = input.replace(";", "");

            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM patients WHERE name = '" + input + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}