public class Film {
    private int id;
    private String judul, genre;
    private int durasi;

    public Film(String judul, String genre, int durasi) {
        this.judul = judul;
        this.genre = genre;
        this.durasi = durasi;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getGenre() { return genre; }
    public int getDurasi() { return durasi; }
    
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return judul + " (" + genre + ", " + durasi + " menit)";
    }
}
