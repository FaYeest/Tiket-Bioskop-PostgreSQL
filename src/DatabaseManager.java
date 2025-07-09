import java.sql.*;
import java.util.*;
import java.io.*;

public class DatabaseManager {
    private static String URL = "jdbc:postgresql://localhost:5432/bioskop_db";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "your_password"; // Ganti dengan password PostgreSQL Anda
    private static Connection connection = null;
    
    static {
        loadDatabaseConfig();
    }
    
    // Load konfigurasi database dari file properties
    private static void loadDatabaseConfig() {
        try {
            Properties props = new Properties();
            File configFile = new File("database.properties");
            
            if (configFile.exists()) {
                props.load(new FileInputStream(configFile));
                URL = props.getProperty("db.url", URL);
                USERNAME = props.getProperty("db.username", USERNAME);
                PASSWORD = props.getProperty("db.password", PASSWORD);
                System.out.println("Konfigurasi database dimuat dari database.properties");
            } else {
                System.out.println("File database.properties tidak ditemukan, menggunakan konfigurasi default");
                System.out.println("Silakan buat file database.properties berdasarkan database.properties.example");
            }
        } catch (IOException e) {
            System.err.println("Error membaca konfigurasi database: " + e.getMessage());
            System.out.println("Menggunakan konfigurasi default");
        }
    }

    // Koneksi ke database
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error koneksi database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Inisialisasi database dan tabel
    public static void initializeDatabase() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            // Buat tabel films
            String createFilmsTable = """
                CREATE TABLE IF NOT EXISTS films (
                    id SERIAL PRIMARY KEY,
                    judul VARCHAR(255) NOT NULL,
                    genre VARCHAR(100) NOT NULL,
                    durasi INTEGER NOT NULL
                )
            """;

            // Buat tabel jadwal
            String createJadwalTable = """
                CREATE TABLE IF NOT EXISTS jadwal (
                    id SERIAL PRIMARY KEY,
                    film_id INTEGER REFERENCES films(id),
                    tanggal VARCHAR(20) NOT NULL,
                    jam VARCHAR(10) NOT NULL,
                    studio INTEGER NOT NULL,
                    kapasitas INTEGER NOT NULL,
                    harga INTEGER NOT NULL
                )
            """;

            // Buat tabel tiket
            String createTiketTable = """
                CREATE TABLE IF NOT EXISTS tiket (
                    id SERIAL PRIMARY KEY,
                    nama_penonton VARCHAR(255) NOT NULL,
                    jadwal_id INTEGER REFERENCES jadwal(id),
                    kursi INTEGER NOT NULL,
                    metode_pembayaran VARCHAR(50) NOT NULL,
                    tanggal_pesan TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;

            // Buat tabel kursi_terpesan untuk tracking kursi yang sudah dipesan
            String createKursiTable = """
                CREATE TABLE IF NOT EXISTS kursi_terpesan (
                    id SERIAL PRIMARY KEY,
                    jadwal_id INTEGER REFERENCES jadwal(id),
                    kursi INTEGER NOT NULL,
                    UNIQUE(jadwal_id, kursi)
                )
            """;

            stmt.execute(createFilmsTable);
            stmt.execute(createJadwalTable);
            stmt.execute(createTiketTable);
            stmt.execute(createKursiTable);

            System.out.println("Database dan tabel berhasil diinisialisasi!");
            
        } catch (SQLException e) {
            System.err.println("Error inisialisasi database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Simpan film ke database
    public static int saveFilm(Film film) {
        String sql = "INSERT INTO films (judul, genre, durasi) VALUES (?, ?, ?) RETURNING id";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, film.getJudul());
            pstmt.setString(2, film.getGenre());
            pstmt.setInt(3, film.getDurasi());
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error menyimpan film: " + e.getMessage());
        }
        return -1;
    }

    // Load semua film dari database
    public static List<Film> loadFilms() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM films ORDER BY id";
        
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Film film = new Film(
                    rs.getString("judul"),
                    rs.getString("genre"),
                    rs.getInt("durasi")
                );
                film.setId(rs.getInt("id")); // Akan ditambahkan ke kelas Film
                films.add(film);
            }
        } catch (SQLException e) {
            System.err.println("Error memuat film: " + e.getMessage());
        }
        return films;
    }

    // Simpan jadwal ke database
    public static int saveJadwal(Jadwal jadwal, int filmId) {
        String sql = "INSERT INTO jadwal (film_id, tanggal, jam, studio, kapasitas, harga) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, filmId);
            pstmt.setString(2, jadwal.getTanggal());
            pstmt.setString(3, jadwal.getJam());
            pstmt.setInt(4, jadwal.getStudio());
            pstmt.setInt(5, jadwal.getKapasitas());
            pstmt.setInt(6, jadwal.getHarga());
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error menyimpan jadwal: " + e.getMessage());
        }
        return -1;
    }

    // Load semua jadwal dari database
    public static List<Jadwal> loadJadwal() {
        List<Jadwal> jadwalList = new ArrayList<>();
        String sql = """
            SELECT j.*, f.judul, f.genre, f.durasi, f.id as film_id
            FROM jadwal j 
            JOIN films f ON j.film_id = f.id 
            ORDER BY j.id
        """;
        
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Film film = new Film(
                    rs.getString("judul"),
                    rs.getString("genre"),
                    rs.getInt("durasi")
                );
                film.setId(rs.getInt("film_id"));
                
                Jadwal jadwal = new Jadwal(
                    film,
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getInt("studio"),
                    rs.getInt("kapasitas"),
                    rs.getInt("harga")
                );
                jadwal.setId(rs.getInt("id"));
                
                // Load kursi yang sudah terpesan untuk jadwal ini
                jadwal.loadKursiTerpesan();
                jadwalList.add(jadwal);
            }
        } catch (SQLException e) {
            System.err.println("Error memuat jadwal: " + e.getMessage());
        }
        return jadwalList;
    }

    // Simpan tiket ke database
    public static boolean saveTiket(Tiket tiket, int jadwalId) {
        String sql = "INSERT INTO tiket (nama_penonton, jadwal_id, kursi, metode_pembayaran) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, tiket.getNamaPenonton());
            pstmt.setInt(2, jadwalId);
            pstmt.setInt(3, tiket.getKursi());
            pstmt.setString(4, tiket.getMetodePembayaran());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error menyimpan tiket: " + e.getMessage());
            return false;
        }
    }

    // Pesan kursi (simpan ke tabel kursi_terpesan)
    public static boolean pesanKursi(int jadwalId, int kursi) {
        String sql = "INSERT INTO kursi_terpesan (jadwal_id, kursi) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, jadwalId);
            pstmt.setInt(2, kursi);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error memesan kursi: " + e.getMessage());
            return false;
        }
    }

    // Cek apakah kursi tersedia
    public static boolean kursiTersedia(int jadwalId, int kursi) {
        String sql = "SELECT COUNT(*) FROM kursi_terpesan WHERE jadwal_id = ? AND kursi = ?";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, jadwalId);
            pstmt.setInt(2, kursi);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Kursi tersedia jika count = 0
            }
        } catch (SQLException e) {
            System.err.println("Error cek kursi: " + e.getMessage());
        }
        return false;
    }

    // Load kursi terpesan untuk jadwal tertentu
    public static List<Integer> loadKursiTerpesan(int jadwalId) {
        List<Integer> kursiTerpesan = new ArrayList<>();
        String sql = "SELECT kursi FROM kursi_terpesan WHERE jadwal_id = ?";
        
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, jadwalId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                kursiTerpesan.add(rs.getInt("kursi"));
            }
        } catch (SQLException e) {
            System.err.println("Error memuat kursi terpesan: " + e.getMessage());
        }
        return kursiTerpesan;
    }

    // Load riwayat tiket berdasarkan nama penonton
    public static List<Tiket> loadRiwayatTiket(String namaPenonton) {
        List<Tiket> riwayat = new ArrayList<>();
        String sql = """
            SELECT t.*, j.tanggal, j.jam, j.studio, j.harga, 
                   f.judul, f.genre, f.durasi
            FROM tiket t
            JOIN jadwal j ON t.jadwal_id = j.id
            JOIN films f ON j.film_id = f.id
            WHERE LOWER(t.nama_penonton) = LOWER(?)
            ORDER BY t.tanggal_pesan DESC
        """;
        
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, namaPenonton);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Film film = new Film(
                    rs.getString("judul"),
                    rs.getString("genre"),
                    rs.getInt("durasi")
                );
                
                Jadwal jadwal = new Jadwal(
                    film,
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getInt("studio"),
                    0, // kapasitas tidak diperlukan untuk display
                    rs.getInt("harga")
                );
                
                Tiket tiket = new Tiket(
                    rs.getString("nama_penonton"),
                    jadwal,
                    rs.getInt("kursi"),
                    rs.getString("metode_pembayaran")
                );
                
                riwayat.add(tiket);
            }
        } catch (SQLException e) {
            System.err.println("Error memuat riwayat tiket: " + e.getMessage());
        }
        return riwayat;
    }

    // Tutup koneksi
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Koneksi database ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Error menutup koneksi: " + e.getMessage());
        }
    }
}
