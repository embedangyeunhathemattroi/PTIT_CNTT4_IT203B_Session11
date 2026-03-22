import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class B2_Pharmacy_Catalogue {
    // //gthich vde 1: `if` chỉ gọi `rs.next()` **1 lần** → chỉ lấy **1 dòng đầu
    // tiên** nên không đủ để in danh sách.

    // `ResultSet` ban đầu đứng trước dòng đầu, mỗi lần `next()` sẽ xuống 1 dòng và
    // trả về `true` nếu còn dữ liệu.

    // 👉 Vì vậy phải dùng `while(rs.next())` để duyệt hết tất cả các dòng.

    public void printAllMedicines() {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ DBContext
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test_jdbc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    "123456");

            // Tạo Statement
            stmt = conn.createStatement();

            String sql = "SELECT pc.medicine_name, s.stock_quantity "
                    + "FROM pharmacy_catalogue pc "
                    + "JOIN stock s ON pc.medicine_id = s.medicine_id";

            rs = stmt.executeQuery(sql);

            boolean hasData = false;

            // Duyệt dữ liệu
            while (rs.next()) {
                hasData = true;

                String name = rs.getString("medicine_name");
                int stock = rs.getInt("stock_quantity");

                System.out.println("Medicine: " + name + " | Stock: " + stock);
            }

            // Nếu không có dữ liệu
            if (!hasData) {
                System.out.println(" Không có dữ liệu!");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // Đóng tài nguyên
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