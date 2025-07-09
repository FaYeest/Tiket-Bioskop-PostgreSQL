# 🎬 Sistem Tiket Bioskop XXI - Database Version

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-007396?style=for-the-badge&logo=java&logoColor=white)

Aplikasi Java untuk sistem pemesanan tiket bioskop yang terintegrasi dengan database PostgreSQL.

## ✨ Fitur Utama

### 👨‍💼 Admin
- ➕ Tambah film baru
- 📅 Tambah jadwal tayang
- 📋 Lihat daftar film dan jadwal
- 💾 Data tersimpan permanen di database

### 🎭 Penonton  
- 🎫 Pesan tiket dengan pilihan kursi
- 📜 Lihat riwayat pemesanan
- 💳 Berbagai metode pembayaran (Cash/QRIS/Debit)
- 🔒 Data pemesanan tersimpan di database

## 🛠️ Teknologi yang Digunakan
- **Java** - Bahasa pemrograman utama
- **PostgreSQL** - Database untuk penyimpanan data
- **JDBC** - Java Database Connectivity untuk koneksi database
- **pgAdmin4** - Tool administrasi database

## 🚀 Setup dan Instalasi

### Prasyarat
- ☕ Java Development Kit (JDK) 8+
- 🐘 PostgreSQL 12+
- 🔧 pgAdmin4
- 📦 Driver PostgreSQL JDBC (sudah disediakan)

### Quick Start
1. **Setup Database:**
   - Buka pgAdmin4
   - Buat database `bioskop_db`
   - Jalankan script `database_setup.sql`

2. **Konfigurasi Koneksi:**
   ```cmd
   copy database.properties.example database.properties
   ```
   Edit file `database.properties` dan sesuaikan password PostgreSQL Anda.

3. **Jalankan Aplikasi:**
   ```cmd
   run.bat
   ```
   Atau manual:
   ```cmd
   javac -cp "lib\postgresql-42.7.7.jar" -d bin src\*.java
   java -cp "bin;lib\postgresql-42.7.7.jar" Main
   ```

## 📁 Folder Structure

```
tiket_bioskop/
├── src/                           # Source code Java
│   ├── Main.java                  # Main application
│   ├── Film.java                  # Film entity
│   ├── Jadwal.java               # Schedule entity  
│   ├── Tiket.java                # Ticket entity
│   └── DatabaseManager.java      # Database operations
├── lib/                          # Dependencies
│   └── postgresql-42.7.7.jar    # PostgreSQL JDBC driver
├── bin/                          # Compiled classes
├── database_setup.sql            # Database schema script
├── database.properties.example   # Database config template
├── DATABASE_SETUP.md            # Database setup guide
├── GITHUB_SETUP.md              # GitHub push guide
├── run.bat                      # Run script for Windows
└── README.md                    # This file
```

## 📊 Database Schema

### 🗃️ Tables
- **films**: Master data film (judul, genre, durasi)
- **jadwal**: Jadwal tayang film dengan studio dan harga
- **tiket**: Data pemesanan tiket penonton
- **kursi_terpesan**: Tracking kursi yang sudah dipesan

### 🔗 Features
- **✅ Persistent Storage**: Data tersimpan permanen di database
- **✅ Referential Integrity**: Foreign key constraints
- **✅ Sample Data**: Data film dan jadwal untuk testing
- **✅ Reporting View**: View untuk laporan transaksi

## 📖 Dokumentasi Lengkap
- 📋 [Setup Database Detail](DATABASE_SETUP.md) - Panduan setup database yang lengkap
- 🔧 [Troubleshooting Guide](DATABASE_SETUP.md#troubleshooting) - Solusi masalah umum
- 🚀 [GitHub Setup Guide](GITHUB_SETUP.md) - Cara push project ke GitHub

## 🛠️ Getting Started (VS Code Java)

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

### Folder Structure

The workspace contains folders with specific purposes:

- `src`: the folder to maintain sources  
- `lib`: the folder to maintain dependencies (includes PostgreSQL JDBC driver)
- `bin`: compiled output files
- `database_setup.sql`: SQL script untuk setup database
- `DATABASE_SETUP.md`: Dokumentasi lengkap setup database

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

### Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## ⚠️ Troubleshooting
Jika mengalami masalah koneksi database, pastikan:
1. ✅ PostgreSQL service berjalan
2. ✅ Database `bioskop_db` sudah dibuat
3. ✅ File `database.properties` sudah dikonfigurasi dengan benar
4. ✅ Password PostgreSQL sudah benar

## 🤝 Contributing
1. Fork repository
2. Buat feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📜 License
Project ini menggunakan MIT License. Lihat file `LICENSE` untuk detail.

## 📧 Contact
Untuk pertanyaan atau saran, silakan buat issue di repository ini.

---
Made with ❤️ using Java and PostgreSQL
