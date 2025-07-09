-- Script Setup Database untuk Sistem Tiket Bioskop
-- Jalankan script ini di pgAdmin4

-- 1. Buat database baru (jalankan di database default seperti 'postgres')
CREATE DATABASE bioskop_db;

-- 2. Connect ke database bioskop_db, lalu jalankan perintah di bawah ini

-- Buat tabel films
CREATE TABLE IF NOT EXISTS films (
    id SERIAL PRIMARY KEY,
    judul VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    durasi INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Buat tabel jadwal
CREATE TABLE IF NOT EXISTS jadwal (
    id SERIAL PRIMARY KEY,
    film_id INTEGER REFERENCES films(id) ON DELETE CASCADE,
    tanggal VARCHAR(20) NOT NULL,
    jam VARCHAR(10) NOT NULL,
    studio INTEGER NOT NULL,
    kapasitas INTEGER NOT NULL,
    harga INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Buat tabel tiket
CREATE TABLE IF NOT EXISTS tiket (
    id SERIAL PRIMARY KEY,
    nama_penonton VARCHAR(255) NOT NULL,
    jadwal_id INTEGER REFERENCES jadwal(id) ON DELETE CASCADE,
    kursi INTEGER NOT NULL,
    metode_pembayaran VARCHAR(50) NOT NULL,
    tanggal_pesan TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Buat tabel kursi_terpesan untuk tracking kursi yang sudah dipesan
CREATE TABLE IF NOT EXISTS kursi_terpesan (
    id SERIAL PRIMARY KEY,
    jadwal_id INTEGER REFERENCES jadwal(id) ON DELETE CASCADE,
    kursi INTEGER NOT NULL,
    tanggal_pesan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(jadwal_id, kursi)
);

-- Insert data sample untuk testing
INSERT INTO films (judul, genre, durasi) VALUES
('Spiderman: No Way Home', 'Action', 148),
('Dune', 'Sci-Fi', 155),
('The Batman', 'Action', 176),
('Turning Red', 'Animation', 100);

INSERT INTO jadwal (film_id, tanggal, jam, studio, kapasitas, harga) VALUES
(1, '2025-07-10', '14:00', 1, 50, 45000),
(1, '2025-07-10', '19:00', 1, 50, 50000),
(2, '2025-07-11', '16:00', 2, 60, 40000),
(3, '2025-07-11', '21:00', 3, 40, 55000),
(4, '2025-07-12', '10:00', 1, 30, 35000);

-- Indexes untuk performance
CREATE INDEX idx_jadwal_film_id ON jadwal(film_id);
CREATE INDEX idx_tiket_jadwal_id ON tiket(jadwal_id);
CREATE INDEX idx_tiket_nama ON tiket(nama_penonton);
CREATE INDEX idx_kursi_jadwal_id ON kursi_terpesan(jadwal_id);

-- View untuk laporan
CREATE VIEW v_laporan_tiket AS
SELECT 
    t.id,
    t.nama_penonton,
    f.judul as film,
    j.tanggal,
    j.jam,
    j.studio,
    t.kursi,
    j.harga,
    t.metode_pembayaran,
    t.tanggal_pesan
FROM tiket t
JOIN jadwal j ON t.jadwal_id = j.id
JOIN films f ON j.film_id = f.id
ORDER BY t.tanggal_pesan DESC;

-- Grant permissions (optional, adjust based on your user setup)
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO your_username;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO your_username;

COMMENT ON DATABASE bioskop_db IS 'Database untuk Sistem Tiket Bioskop XXI';
COMMENT ON TABLE films IS 'Tabel master film';
COMMENT ON TABLE jadwal IS 'Tabel jadwal tayang film';
COMMENT ON TABLE tiket IS 'Tabel transaksi pemesanan tiket';
COMMENT ON TABLE kursi_terpesan IS 'Tabel tracking kursi yang sudah dipesan untuk setiap jadwal';
