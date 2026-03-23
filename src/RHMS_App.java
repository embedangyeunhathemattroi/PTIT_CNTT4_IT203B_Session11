import java.sql.*;
import java.util.Scanner;

public class RHMS_App {

    static final String URL = "jdbc:mysql://localhost:3306/test_b5?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "123456";

    // 1. Danh sách bệnh nhân
    public static void showPatients() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM patients");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(
                        rs.getInt("patient_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("department"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. Thêm bệnh nhân (PreparedStatement chống SQL Injection)
    public static void addPatient(Scanner sc) {
        String sql = "INSERT INTO patients(name, age, department, disease, admission_days) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.print("Tên: ");
            String name = sc.nextLine(); // xử lý được L'Oréal

            System.out.print("Tuổi: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Khoa: ");
            String dept = sc.nextLine();

            System.out.print("Bệnh: ");
            String dis = sc.nextLine();

            System.out.print("Số ngày nhập viện: ");
            int days = Integer.parseInt(sc.nextLine());

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, dept);
            ps.setString(4, dis);
            ps.setInt(5, days);

            ps.executeUpdate();

            System.out.println("Thêm thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Cập nhật bệnh án
    public static void updateDisease(Scanner sc) {
        String sql = "UPDATE patients SET disease = ? WHERE patient_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.print("Nhập ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Bệnh mới: ");
            String disease = sc.nextLine();

            ps.setString(1, disease);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Cập nhật thành công!");
            else
                System.out.println("Không tìm thấy!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Xuất viện + tính phí (CallableStatement)
    public static void discharge(Scanner sc) {
        String sql = "{CALL CALCULATE_DISCHARGE_FEE(?, ?)}";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                CallableStatement cs = conn.prepareCall(sql)) {

            System.out.print("Nhập số ngày nằm viện: ");
            int days = Integer.parseInt(sc.nextLine());

            cs.setInt(1, days);
            cs.registerOutParameter(2, Types.DOUBLE);

            cs.execute();

            double fee = cs.getDouble(2);

            System.out.println(" Viện phí: " + fee);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MENU
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== RHMS MENU =====");
            System.out.println("1. Danh sách bệnh nhân");
            System.out.println("2. Thêm bệnh nhân");
            System.out.println("3. Cập nhật bệnh án");
            System.out.println("4. Xuất viện & tính phí");
            System.out.println("5. Thoát");

            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    showPatients();
                    break;
                case 2:
                    addPatient(sc);
                    break;
                case 3:
                    updateDisease(sc);
                    break;
                case 4:
                    discharge(sc);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Sai lựa chọn!");
            }
        }
    }
}