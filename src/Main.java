import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    static List<Film> daftarFilm = new ArrayList<>();
    static List<Jadwal> daftarJadwal = new ArrayList<>();

    public static void main(String[] args) {
        // Inisialisasi database
        DatabaseManager.initializeDatabase();

        // Load data dari database
        daftarFilm = DatabaseManager.loadFilms();
        daftarJadwal = DatabaseManager.loadJadwal();

        while (true) {
            System.out.println("\n=== Sistem Tiket Bioskop XXI ===");
            System.out.print("Masuk sebagai (Admin/Penonton/Keluar): ");
            String peran = input.nextLine().trim().toLowerCase();

            if (peran.equals("admin")) {
                System.out.print("Masukkan nama admin: ");
                String nama = input.nextLine();
                User user = new Admin(nama, daftarFilm, daftarJadwal);
                user.menu();
            }
            else if (peran.equals("penonton")) {
                System.out.print("Nama Anda: ");
                String nama = input.nextLine();
                User user = new Penonton(nama, daftarJadwal);
                user.menu();
            }
            else if (peran.equals("keluar")) {
                DatabaseManager.closeConnection();
                break;
            }
            else System.out.println("Peran tidak dikenali.");
        }
    }
}
