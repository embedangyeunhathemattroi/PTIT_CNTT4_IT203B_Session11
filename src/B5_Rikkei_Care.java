import java.sql.*;
import java.util.Scanner;

public class B5_Rikkei_Care {

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/test_jdbc?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }

    public static void showDoctors() {
        String sql = "SELECT * FROM doctors";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                System.out.println(
                        rs.getString("doctor_id") + " | "
                                + rs.getString("name") + " | "
                                + rs.getString("specialty"));
            }

            if (!hasData) {
                System.out.println("Không có dữ liệu!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi lấy dữ liệu!");
        }
    }

    public static void addDoctor(Scanner sc) {
        try {
            System.out.print("Nhập ID: ");
            String id = sc.nextLine();

            System.out.print("Nhập tên: ");
            String name = sc.nextLine();

            System.out.print("Nhập chuyên khoa: ");
            String sp = sc.nextLine();

            // kiểm tra rỗng
            if (id.isEmpty() || name.isEmpty() || sp.isEmpty()) {
                System.out.println(" Không được để trống!");
                return;
            }

            String sql = "INSERT INTO doctors VALUES (?, ?, ?)";

            try (Connection conn = getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, id);
                ps.setString(2, name);
                ps.setString(3, sp);

                ps.executeUpdate();
                System.out.println("Thêm thành công!");

            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(" Trùng mã bác sĩ!");
            }

        } catch (Exception e) {
            System.out.println(" Lỗi nhập dữ liệu!");
        }
    }

    // 3. Thống kê chuyên khoa
    public static void statistics() {
        String sql = "SELECT specialty, COUNT(*) AS total FROM doctors GROUP BY specialty";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("=== THỐNG KÊ ===");

            while (rs.next()) {
                System.out.println(
                        rs.getString("specialty") + " : "
                                + rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi thống kê!");
        }
    }

    // MENU
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ");
            System.out.println("3. Thống kê chuyên khoa");
            System.out.println("4. Thoát");

            System.out.print("Chọn: ");
            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println(" Nhập sai!");
                continue;
            }

            switch (choice) {
                case 1:
                    showDoctors();
                    break;
                case 2:
                    addDoctor(sc);
                    break;
                case 3:
                    statistics();
                    break;
                case 4:
                    System.out.println("Thoát...");
                    return;
                default:
                    System.out.println(" Sai lựa chọn!");
            }
        }
    }
}