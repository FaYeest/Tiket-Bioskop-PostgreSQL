public class Tiket {
    private String namaPenonton;
    private Jadwal jadwal;
    private int kursi;
    private String metodePembayaran;

    public Tiket(String namaPenonton, Jadwal jadwal, int kursi, String metodePembayaran) {
        this.namaPenonton = namaPenonton;
        this.jadwal = jadwal;
        this.kursi = kursi;
        this.metodePembayaran = metodePembayaran;
    }

    public String getNamaPenonton() {
        return namaPenonton;
    }
    
    public int getKursi() {
        return kursi;
    }
    
    public String getMetodePembayaran() {
        return metodePembayaran;
    }
    
    public Jadwal getJadwal() {
        return jadwal;
    }

    public void tampilkan() {
        System.out.println("\n--- Tiket Bioskop ---");
        System.out.println("Nama     : " + namaPenonton);
        System.out.println("Film     : " + jadwal.getFilm().getJudul());
        System.out.println("Tanggal  : " + jadwal.getTanggal());
        System.out.println("Jam      : " + jadwal.getJam());
        System.out.println("Studio   : " + jadwal.getStudio());
        System.out.println("Kursi    : " + kursi);
        System.out.println("Pembayaran: " + metodePembayaran);
        System.out.println("Harga Tiket : Rp" + jadwal.getHarga());
    }
}
