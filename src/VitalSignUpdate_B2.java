import java.sql.*;

public class VitalSignUpdate_B2 {
    // PreparedStatement giải quyết vì:

    // setDouble(), setInt() truyền dữ liệu đúng kiểu số
    // JDBC tự chuyển về định dạng chuẩn của SQL (dấu chấm .)
    // Không phụ thuộc Locale (Việt/Pháp)

    public static void updateVitalSigns(int id, double temp, int heartRate) {

        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE vital_signs SET temperature = ?, heart_rate = ? WHERE patient_id = ?";

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test_jdbc",
                    "root",
                    "123456");

            ps = conn.prepareStatement(sql);

            ps.setDouble(1, temp);
            ps.setInt(2, heartRate);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(" Cập nhật thành công!");
            } else {
                System.out.println(" Không tìm thấy bệnh nhân!");
            }

        } catch (Exception e) {
            System.out.println(" Lỗi hệ thống!");
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        updateVitalSigns(1, 37.5, 90);
    }
}