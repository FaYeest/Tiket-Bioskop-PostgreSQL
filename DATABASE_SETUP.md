# Setup Database PostgreSQL untuk Sistem Tiket Bioskop

## Prasyarat
1. PostgreSQL sudah terinstall
2. pgAdmin4 sudah terinstall
3. Java Development Kit (JDK) 8 atau lebih baru
4. Driver PostgreSQL JDBC (sudah disediakan di folder lib/)

## Langkah-langkah Setup

### 1. Setup Database di pgAdmin4

1. **Buka pgAdmin4**
2. **Connect ke PostgreSQL server** (biasanya localhost)
3. **Buat database baru:**
   - Klik kanan pada "Databases"
   - Pilih "Create" > "Database..."
   - Nama database: `bioskop_db`
   - Klik "Save"

4. **Jalankan script SQL:**
   - Klik kanan pada database `bioskop_db`
   - Pilih "Query Tool"
   - Copy dan paste isi file `database_setup.sql`
   - Klik tombol "Execute" (F5)

### 2. Konfigurasi Koneksi Database

1. **Copy file konfigurasi:**
   ```cmd
   copy database.properties.example database.properties
   ```

2. **Edit file `database.properties`:**
   ```properties
   db.url=jdbc:postgresql://localhost:5432/bioskop_db
   db.username=postgres
   db.password=PASSWORD_ANDA_DISINI
   ```
   
   **Ganti `PASSWORD_ANDA_DISINI` dengan password PostgreSQL Anda**

### 3. Kompilasi dan Jalankan Program

1. **Buka Command Prompt di folder project**
2. **Kompilasi semua file Java:**
   ```cmd
   javac -cp "lib\postgresql-42.7.7.jar" -d bin src\*.java
   ```

3. **Jalankan program:**
   ```cmd
   java -cp "bin;lib\postgresql-42.7.7.jar" Main
   ```

## Struktur Database

### Tabel `films`
- `id` (SERIAL PRIMARY KEY)
- `judul` (VARCHAR)
- `genre` (VARCHAR)
- `durasi` (INTEGER)
- `created_at` (TIMESTAMP)

### Tabel `jadwal`
- `id` (SERIAL PRIMARY KEY)
- `film_id` (INTEGER, FK ke films)
- `tanggal` (VARCHAR)
- `jam` (VARCHAR)
- `studio` (INTEGER)
- `kapasitas` (INTEGER)
- `harga` (INTEGER)
- `created_at` (TIMESTAMP)

### Tabel `tiket`
- `id` (SERIAL PRIMARY KEY)
- `nama_penonton` (VARCHAR)
- `jadwal_id` (INTEGER, FK ke jadwal)
- `kursi` (INTEGER)
- `metode_pembayaran` (VARCHAR)
- `tanggal_pesan` (TIMESTAMP)

### Tabel `kursi_terpesan`
- `id` (SERIAL PRIMARY KEY)
- `jadwal_id` (INTEGER, FK ke jadwal)
- `kursi` (INTEGER)
- `tanggal_pesan` (TIMESTAMP)

## Fitur Database

### 1. Persistent Storage
- Semua data film, jadwal, tiket tersimpan di database
- Data tidak hilang saat program ditutup

### 2. Referential Integrity
- Foreign key constraints memastikan konsistensi data
- Cascade delete untuk data terkait

### 3. Data Sample
- Database sudah terisi data sample untuk testing
- 4 film dengan 5 jadwal berbeda

### 4. View Laporan
- View `v_laporan_tiket` untuk melihat semua transaksi
- Query: `SELECT * FROM v_laporan_tiket;`

## Troubleshooting

### Error "FATAL: password authentication failed"
- Pastikan password di `database.properties` benar
- Pastikan user postgres memiliki akses ke database

### Error "Connection refused"
- Pastikan PostgreSQL service berjalan
- Periksa port (default: 5432)
- Periksa hostname (default: localhost)

### Error "ClassNotFoundException: org.postgresql.Driver"
- Pastikan file `postgresql-42.7.7.jar` ada di folder `lib/`
- Pastikan classpath sudah benar saat kompilasi dan run

### Error "database bioskop_db does not exist"
- Pastikan database `bioskop_db` sudah dibuat di pgAdmin4
- Jalankan script `database_setup.sql`

## Query Berguna untuk pgAdmin4

### Lihat semua tiket yang sudah dipesan:
```sql
SELECT * FROM v_laporan_tiket;
```

### Lihat kursi terpesan untuk jadwal tertentu:
```sql
SELECT j.id, f.judul, j.tanggal, j.jam, 
       array_agg(kt.kursi ORDER BY kt.kursi) as kursi_terpesan
FROM jadwal j
JOIN films f ON j.film_id = f.id
LEFT JOIN kursi_terpesan kt ON j.id = kt.jadwal_id
GROUP BY j.id, f.judul, j.tanggal, j.jam;
```

### Reset data (hati-hati!):
```sql
TRUNCATE TABLE kursi_terpesan, tiket, jadwal, films RESTART IDENTITY CASCADE;
```

## Tips Penggunaan

1. **Backup Database Reguler:**
   - Klik kanan database > "Backup..."
   - Pilih format "Custom" untuk file yang lebih kecil

2. **Monitor Performance:**
   - Gunakan pgAdmin4 untuk melihat query yang berjalan
   - Periksa EXPLAIN PLAN untuk query yang lambat

3. **Security:**
   - Jangan commit file `database.properties` ke version control
   - Gunakan user database dengan privilege minimal

4. **Development:**
   - Gunakan data sample untuk testing
   - Reset data saat diperlukan dengan script reset
