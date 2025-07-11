import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    private static Scanner input = new Scanner(System.in);
    private List<Film> daftarFilm;
    private List<Jadwal> daftarJadwal;

    public Admin(String nama, List<Film> daftarFilm, List<Jadwal> daftarJadwal) {
        super(nama);
        this.daftarFilm = daftarFilm;
        this.daftarJadwal = daftarJadwal;
    }

    @Override
    public void menu() {
        System.out.println("Halo admin " + nama);

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

    private void tambahFilm() {
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

    private void tambahJadwal() {
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

    private void tampilkanFilm() {
        System.out.println("\nDaftar Film:");
        for (int i = 0; i < daftarFilm.size(); i++)
            System.out.println((i + 1) + ". " + daftarFilm.get(i));
    }

    private void tampilkanJadwal() {
        System.out.println("\nDaftar Jadwal:");
        for (int i = 0; i < daftarJadwal.size(); i++)
            System.out.println((i + 1) + ". " + daftarJadwal.get(i));
    }
}
