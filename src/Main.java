import java.util.*;
public class Main {
    static Scanner input = new Scanner(System.in);
    static List<Film> daftarFilm = new ArrayList<>();
    static List<Jadwal> daftarJadwal = new ArrayList<>();
    static List<Tiket> riwayatTiket = new ArrayList<>();

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

            if (peran.equals("admin")) adminMenu();
            else if (peran.equals("penonton")) penontonMenu();
            else if (peran.equals("keluar")) {
                DatabaseManager.closeConnection();
                break;
            }
            else System.out.println("Peran tidak dikenali.");
        }
    }

    static void adminMenu() {
        System.out.print("Masukkan nama admin: ");
        String admin = input.nextLine();
        System.out.println("Halo admin " + admin);

        while (true) {
            System.out.println("\n1. Tambah Film\n2. Tambah Jadwal\n3. Lihat Film\n4. Lihat Jadwal\n0. Kembali");
            System.out.print("Pilih menu: ");
            String pilih = input.nextLine();
            if (pilih.equals("1")) tambahFilm();
            else if (pilih.equals("2")) tambahJadwal();
            else if (pilih.equals("3")) tampilkanFilm();
            else if (pilih.equals("4")) tampilkanJadwal();
            else if (pilih.equals("0")) break;
        }
    }

    static void tambahFilm() {
        System.out.print("Judul : ");
        String judul = input.nextLine();
        System.out.print("Genre : ");
        String genre = input.nextLine();
        System.out.print("Durasi (menit): ");
        int durasi = Integer.parseInt(input.nextLine());
        
        Film film = new Film(judul, genre, durasi);
        int filmId = DatabaseManager.saveFilm(film);
        
        if (filmId > 0) {
            film.setId(filmId);
            daftarFilm.add(film);
            System.out.println("Film berhasil ditambahkan!");
        } else {
            System.out.println("Gagal menambahkan film!");
        }
    }

    static void tambahJadwal() {
        if (daftarFilm.isEmpty()) {
            System.out.println("Belum ada film! Tambahkan film terlebih dahulu.");
            return;
        }
        
        tampilkanFilm();
        System.out.print("Pilih no film: ");
        int i = Integer.parseInt(input.nextLine()) - 1;
        
        if (i < 0 || i >= daftarFilm.size()) {
            System.out.println("Pilihan film tidak valid!");
            return;
        }
        
        Film selectedFilm = daftarFilm.get(i);
        
        System.out.print("Tanggal: ");
        String tgl = input.nextLine();
        System.out.print("Jam: ");
        String jam = input.nextLine();
        System.out.print("Studio: ");
        int studio = Integer.parseInt(input.nextLine());
        System.out.print("Kapasitas: ");
        int kapasitas = Integer.parseInt(input.nextLine());
        System.out.print("Harga tiket: ");
        int harga = Integer.parseInt(input.nextLine());
        
        Jadwal jadwal = new Jadwal(selectedFilm, tgl, jam, studio, kapasitas, harga);
        int jadwalId = DatabaseManager.saveJadwal(jadwal, selectedFilm.getId());
        
        if (jadwalId > 0) {
            jadwal.setId(jadwalId);
            daftarJadwal.add(jadwal);
            System.out.println("Jadwal berhasil ditambahkan!");
        } else {
            System.out.println("Gagal menambahkan jadwal!");
        }
    }

    static void tampilkanFilm() {
        System.out.println("\nDaftar Film:");
        for (int i = 0; i < daftarFilm.size(); i++)
            System.out.println((i + 1) + ". " + daftarFilm.get(i));
    }

    static void tampilkanJadwal() {
        System.out.println("\nDaftar Jadwal:");
        for (int i = 0; i < daftarJadwal.size(); i++)
            System.out.println((i + 1) + ". " + daftarJadwal.get(i));
    }

    static void penontonMenu() {
        System.out.print("Nama Anda: ");
        String nama = input.nextLine();

        while (true) {
            System.out.println("\n1. Pesan Tiket");
            System.out.println("2. Lihat Riwayat Pemesanan");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");
            String menu = input.nextLine();

            if (menu.equals("1")) {
                if (daftarJadwal.isEmpty()) {
                    System.out.println("Belum ada jadwal film tersedia!");
                    continue;
                }
                
                tampilkanJadwal();
                System.out.print("Pilih no jadwal: ");
                int i = Integer.parseInt(input.nextLine()) - 1;
                
                if (i < 0 || i >= daftarJadwal.size()) {
                    System.out.println("Pilihan jadwal tidak valid!");
                    continue;
                }
                
                Jadwal jadwal = daftarJadwal.get(i);
                System.out.print("Pilih kursi (1-" + jadwal.getKapasitas() + "): ");
                int kursi = Integer.parseInt(input.nextLine());
                
                if (!jadwal.kursiTersedia(kursi)) {
                    System.out.println("Kursi tidak tersedia.");
                    continue;
                }
                
                System.out.print("Metode pembayaran (Cash/QRIS/Debit): ");
                String bayar = input.nextLine();
                
                // Pesan kursi terlebih dahulu
                jadwal.pesanKursi(kursi);
                
                // Buat tiket
                Tiket tiket = new Tiket(nama, jadwal, kursi, bayar);
                
                // Simpan tiket ke database
                boolean berhasil = DatabaseManager.saveTiket(tiket, jadwal.getId());
                
                if (berhasil) {
                    tiket.tampilkan();
                    System.out.println("Tiket berhasil dipesan!");
                } else {
                    System.out.println("Gagal memesan tiket!");
                }
            } else if (menu.equals("2")) {
                System.out.println("\n--- Riwayat Pemesanan ---");
                List<Tiket> riwayat = DatabaseManager.loadRiwayatTiket(nama);
                
                if (riwayat.isEmpty()) {
                    System.out.println("Belum ada pemesanan.");
                } else {
                    for (Tiket t : riwayat) {
                        t.tampilkan();
                        System.out.println("---");
                    }
                }
            } else if (menu.equals("0")) {
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
