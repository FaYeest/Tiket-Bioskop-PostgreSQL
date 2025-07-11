import java.util.List;
import java.util.Scanner;

public class Penonton extends User {
    private static Scanner input = new Scanner(System.in);
    private List<Jadwal> daftarJadwal;

    public Penonton(String nama, List<Jadwal> daftarJadwal) {
        super(nama);
        this.daftarJadwal = daftarJadwal;
    }

    @Override
    public void menu() {
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

    private void tampilkanJadwal() {
        System.out.println("\nDaftar Jadwal:");
        for (int i = 0; i < daftarJadwal.size(); i++)
            System.out.println((i + 1) + ". " + daftarJadwal.get(i));
    }
}
