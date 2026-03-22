import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class B3_BedManagement {

    public void updateBedStatus(String bedId) {

        String url = "jdbc:mysql://localhost:3306/test_jdbc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "123456";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            String sql = "UPDATE beds SET bed_status = 'Dang su dung' WHERE bed_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bedId);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println(" Mã giường không tồn tại!");
            } else {
                System.out.println(" Cập nhật thành công!");
            }

        } catch (Exception e) {
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
}