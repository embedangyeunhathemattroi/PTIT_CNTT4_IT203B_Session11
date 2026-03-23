import java.sql.*;

public class DoctorLogin_B1 {
    // PreparedStatement tách riêng câu lệnh SQL và dữ liệu đầu vào
    // SQL được biên dịch trước (pre-compiled) → cấu trúc câu lệnh đã cố định
    // Giá trị người dùng nhập (?) chỉ được coi là dữ liệu, không thể biến thành
    // lệnh SQL

    // Vì vậy:' OR '1'='1 → chỉ là chuỗi bình thường, KHÔNG phá được WHERE
    public static boolean login(String code, String pass) {
        boolean isLogin = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM doctors WHERE doctor_code = ? AND password = ?";

        try {
            // Tạo kết nối TRƯỚC
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test_jdbc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    "123456");

            // Dùng PreparedStatement
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Đăng nhập thành công!");
                isLogin = true;
            } else {
                System.out.println(" Sai tài khoản hoặc mật khẩu!");
            }

        } catch (Exception e) {
            System.out.println(" Lỗi hệ thống!");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return isLogin;
    }

    public static void main(String[] args) {

        // Đúng
        login("DOC001", "123456");

        // Sai
        login("DOC001", "111111");

        // Test SQL Injection
        login("DOC001", "' OR '1'='1");
    }
}