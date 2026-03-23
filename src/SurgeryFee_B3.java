import java.sql.*;

public class SurgeryFee_B3 {
    public static void getSurgeryFee(int surgeryId) {
        // JDBC bắt buộc registerOutParameter() vì:
        // Driver cần biết tham số nào là OUT
        // Và biết kiểu dữ liệu để nhận kết quả từ DB
        Connection conn = null;
        CallableStatement cs = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test_jdbc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    "123456");
            cs = conn.prepareCall("{CALL GET_SURGERY_FEE(?,?)}");
            cs.setInt(1, surgeryId);
            cs.registerOutParameter(2, Types.DOUBLE);
            cs.execute();
            double fee = cs.getDouble(2);
            System.out.println("Phí phẫu thuật: " + fee);
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống!");
            e.printStackTrace();
        } finally {
            try {
                if (cs != null)
                    cs.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        getSurgeryFee(1);
    }

}
