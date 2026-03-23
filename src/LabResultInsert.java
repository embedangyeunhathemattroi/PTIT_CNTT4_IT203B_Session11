import java.sql.*;

public class LabResultInsert {

    public static void insertBatch() {

        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO lab_results(patient_id, test_name, result_value) VALUES (?, ?, ?)";

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test_jdbc",
                    "root",
                    "123456");

            // 🔥 Tạo 1 lần duy nhất
            ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 1000; i++) {

                ps.setInt(1, i);
                ps.setString(2, "Blood Test");
                ps.setDouble(3, 5.5 + i);

                ps.executeUpdate(); // chạy nhanh hơn Statement
            }

            System.out.println("✅ Insert 1000 records thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertBatch();
    }
}