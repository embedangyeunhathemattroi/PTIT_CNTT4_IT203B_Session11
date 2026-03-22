public class App {
    public static void main(String[] args) {

        // Gọi chức năng in danh mục thuốc
        B2_Pharmacy_Catalogue b2 = new B2_Pharmacy_Catalogue();
        b2.printAllMedicines();
        B1_Hospital_DB b1 = new B1_Hospital_DB();
        b1.getConnection();

        B3_BedManagement b3 = new B3_BedManagement();

        b3.updateBedStatus("Bed_001"); // test tồn tại
        b3.updateBedStatus("Bed_999"); // test không tồn tại

        B4_PatientSearch b4 = new B4_PatientSearch();
        b4.findPatient("Nguyen Van A"); // test tìm kiếm bình thường

    }
}