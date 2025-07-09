import java.util.*;
public class Jadwal {
    private int id;
    private Film film;
    private String tanggal, jam;
    private int studio, kapasitas, harga;
    private List<Integer> kursiTerpesan;

    public Jadwal(Film film, String tanggal, String jam, int studio, int kapasitas, int harga) {
        this.film = film;
        this.tanggal = tanggal;
        this.jam = jam;
        this.studio = studio;
        this.kapasitas = kapasitas;
        this.harga = harga;
        this.kursiTerpesan = new ArrayList<>();
    }

    public int getId() { return id; }
    public Film getFilm() { return film; }
    public String getTanggal() { return tanggal; }
    public String getJam() { return jam; }
    public int getStudio() { return studio; }
    public int getKapasitas() { return kapasitas; }
    public int getHarga() { return harga; }
    
    public void setId(int id) { this.id = id; }

    public boolean kursiTersedia(int kursi) {
        if (id > 0) {
            // Jika jadwal sudah ada di database, cek dari database
            return DatabaseManager.kursiTersedia(id, kursi) && kursi >= 1 && kursi <= kapasitas;
        } else {
            // Jika jadwal belum di database, cek dari list lokal
            return !kursiTerpesan.contains(kursi) && kursi >= 1 && kursi <= kapasitas;
        }
    }

    public void pesanKursi(int kursi) {
        if (id > 0) {
            // Simpan ke database
            DatabaseManager.pesanKursi(id, kursi);
        } else {
            // Simpan ke list lokal
            kursiTerpesan.add(kursi);
        }
    }
    
    public void loadKursiTerpesan() {
        if (id > 0) {
            this.kursiTerpesan = DatabaseManager.loadKursiTerpesan(id);
        }
    }

    @Override
    public String toString() {
        return film + " | Studio " + studio + " | " + tanggal + " " + jam + " | Rp" + harga;
    }
}
