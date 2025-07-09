# Panduan Push Project ke GitHub

## Persiapan Sebelum Push

### 1. Install Git (jika belum ada)
- Download Git dari: https://git-scm.com/download/win
- Install dengan pengaturan default
- Buka Command Prompt dan cek instalasi:
  ```cmd
  git --version
  ```

### 2. Konfigurasi Git (jika belum)
```cmd
git config --global user.name "Nama Anda"
git config --global user.email "email@anda.com"
```

### 3. Buat Akun GitHub (jika belum ada)
- Daftar di: https://github.com
- Verify email Anda

## Langkah-langkah Push ke GitHub

### Opsi 1: Buat Repository Baru di GitHub (Recommended)

#### Step 1: Buat Repository di GitHub Web
1. Login ke GitHub
2. Klik tombol **"New"** atau **"+"** di pojok kanan atas
3. Pilih **"New repository"**
4. Isi form:
   - **Repository name**: `tiket-bioskop-java`
   - **Description**: `Sistem Tiket Bioskop XXI dengan Database PostgreSQL`
   - **Public** atau **Private** (pilih sesuai kebutuhan)
   - ✅ **Add a README file** (UNCHECK ini, karena kita sudah punya)
   - ❌ **Add .gitignore** (UNCHECK ini, karena kita sudah punya)
   - ❌ **Choose a license** (bisa ditambah nanti)
5. Klik **"Create repository"**

#### Step 2: Push dari Local ke GitHub
```cmd
# Navigasi ke folder project
cd "c:\Users\Farras\Documents\WORKDESK\pbo\tiket_bioskop"

# Initialize git repository
git init

# Add semua file ke staging
git add .

# Commit pertama
git commit -m "Initial commit: Sistem Tiket Bioskop dengan Database PostgreSQL"

# Tambahkan remote repository (ganti USERNAME dengan username GitHub Anda)
git remote add origin https://github.com/USERNAME/tiket-bioskop-java.git

# Set branch utama
git branch -M main

# Push ke GitHub
git push -u origin main
```

### Opsi 2: Clone Repository yang Sudah Ada (jika repository sudah dibuat)

```cmd
# Clone repository
git clone https://github.com/USERNAME/tiket-bioskop-java.git

# Copy file project ke folder hasil clone
# Lalu:
cd tiket-bioskop-java
git add .
git commit -m "Add complete cinema ticket system"
git push origin main
```

## Template README.md untuk GitHub

Saya akan update README.md Anda agar lebih menarik di GitHub:

### File README.md yang Enhanced
```markdown
# 🎬 Sistem Tiket Bioskop XXI

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

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
- 🔒 Data pemesanan tersimpan aman di database

## 🛠️ Teknologi yang Digunakan
- **Java** - Bahasa pemrograman utama
- **PostgreSQL** - Database untuk penyimpanan data
- **JDBC** - Java Database Connectivity
- **pgAdmin4** - Tool administrasi database

## 🚀 Quick Start

### Prasyarat
- ☕ Java Development Kit (JDK) 8+
- 🐘 PostgreSQL 12+
- 🔧 pgAdmin4

### Instalasi
1. **Clone repository:**
   \`\`\`bash
   git clone https://github.com/USERNAME/tiket-bioskop-java.git
   cd tiket-bioskop-java
   \`\`\`

2. **Setup Database:**
   - Buka pgAdmin4
   - Buat database \`bioskop_db\`
   - Import \`database_setup.sql\`

3. **Konfigurasi:**
   \`\`\`bash
   copy database.properties.example database.properties
   \`\`\`
   Edit password PostgreSQL di \`database.properties\`

4. **Jalankan:**
   \`\`\`bash
   run.bat
   \`\`\`

## 📊 Database Schema

### 🎬 Tables
- **films**: Master data film
- **jadwal**: Jadwal tayang dengan studio dan harga  
- **tiket**: Data pemesanan penonton
- **kursi_terpesan**: Tracking kursi yang sudah dipesan

## 📖 Dokumentasi
- 📋 [Setup Database Lengkap](DATABASE_SETUP.md)
- 🔧 [Troubleshooting Guide](DATABASE_SETUP.md#troubleshooting)

## 🤝 Contributing
1. Fork repository
2. Buat feature branch (\`git checkout -b feature/AmazingFeature\`)
3. Commit changes (\`git commit -m 'Add AmazingFeature'\`)
4. Push ke branch (\`git push origin feature/AmazingFeature\`)
5. Open Pull Request

## 📝 License
Distributed under the MIT License. See \`LICENSE\` for more information.

## 📧 Contact
Nama Anda - email@anda.com

Project Link: [https://github.com/USERNAME/tiket-bioskop-java](https://github.com/USERNAME/tiket-bioskop-java)
```

## ⚠️ File yang Tidak Boleh di-Push

File-file berikut sudah di-exclude di `.gitignore`:
- ✅ `database.properties` (berisi password)
- ✅ `bin/` folder (compiled files)
- ✅ IDE settings
- ✅ Log files

## 🔐 Keamanan

### File Sensitif
- **`database.properties`** berisi password database
- File ini **TIDAK AKAN** ter-push karena sudah ada di `.gitignore`
- User lain harus copy dari `database.properties.example`

### Best Practices
1. Jangan pernah commit password ke GitHub
2. Gunakan environment variables untuk production
3. Update `.gitignore` jika ada file sensitif baru

## 📱 Perintah Git yang Berguna

### Update Kode
```cmd
# Check status
git status

# Add file baru/yang diubah
git add .

# Commit dengan pesan
git commit -m "Deskripsi perubahan"

# Push ke GitHub
git push origin main
```

### Branch Management
```cmd
# Buat branch baru
git checkout -b feature/nama-fitur

# Pindah branch
git checkout main

# Merge branch
git merge feature/nama-fitur
```

### Sinkronisasi
```cmd
# Pull perubahan terbaru
git pull origin main

# Check remote
git remote -v
```

## 🎯 Tips GitHub

1. **README.md yang Menarik:**
   - Gunakan emoji dan badges
   - Sertakan screenshot jika memungkinkan
   - Buat dokumentasi yang jelas

2. **Commit Messages yang Baik:**
   ```
   feat: tambah fitur pembayaran QRIS
   fix: perbaiki bug pemesanan kursi
   docs: update dokumentasi database
   refactor: improve database connection handling
   ```

3. **Releases:**
   - Buat tag untuk versi: `git tag v1.0.0`
   - Push tags: `git push --tags`

4. **Issues dan Projects:**
   - Gunakan GitHub Issues untuk track bugs
   - Gunakan GitHub Projects untuk management

## 🔄 Workflow Pengembangan

1. **Development:**
   ```cmd
   git checkout -b feature/new-feature
   # develop...
   git add .
   git commit -m "Add new feature"
   git push origin feature/new-feature
   ```

2. **Testing:**
   - Test di local environment
   - Pastikan database setup bekerja

3. **Merge:**
   - Buat Pull Request di GitHub
   - Review dan merge ke main branch

Selamat! Project Anda siap untuk dipush ke GitHub! 🚀
