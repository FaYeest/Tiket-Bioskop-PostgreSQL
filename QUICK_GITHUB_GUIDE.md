# 🚀 Quick GitHub Setup Guide

## Langkah Cepat Push ke GitHub

### 1️⃣ Persiapan
- ✅ Install Git: https://git-scm.com/download/win
- ✅ Buat akun GitHub: https://github.com
- ✅ Verifikasi email GitHub

### 2️⃣ Buat Repository di GitHub
1. Login ke GitHub
2. Klik tombol **"New"** (hijau)
3. Repository name: `tiket-bioskop-java`
4. Description: `Sistem Tiket Bioskop XXI dengan Database PostgreSQL`
5. Public/Private (pilih sesuai kebutuhan)
6. **JANGAN** centang "Add a README file"
7. Klik **"Create repository"**

### 3️⃣ Push ke GitHub

#### Opsi A: Menggunakan Script Otomatis
```cmd
git_setup.bat
```
Ikuti instruksi di layar!

#### Opsi B: Manual Command
```cmd
# Navigasi ke folder project
cd "c:\Users\Farras\Documents\WORKDESK\pbo\tiket_bioskop"

# Initialize Git
git init
git add .
git commit -m "Initial commit: Sistem Tiket Bioskop dengan Database PostgreSQL"

# Tambahkan remote (GANTI USERNAME dan REPO-NAME!)
git remote add origin https://github.com/USERNAME/REPO-NAME.git
git branch -M main
git push -u origin main
```

### 4️⃣ Update Kode Selanjutnya
```cmd
git add .
git commit -m "Deskripsi perubahan"
git push origin main
```

## 🔐 File yang Aman
✅ File `database.properties` TIDAK akan ter-upload (sudah di `.gitignore`)
✅ Compiled files di folder `bin/` tidak ter-upload
✅ User lain harus setup database sendiri

## 🎯 Hasil Akhir
Setelah push berhasil, repository GitHub Anda akan berisi:
- 📁 Source code Java
- 📚 Dokumentasi lengkap
- 🛠️ Script setup database
- 🚀 Script untuk menjalankan aplikasi
- 📋 README yang menarik dengan badges

## 🆘 Troubleshooting
| Error | Solusi |
|-------|--------|
| `git: command not found` | Install Git dari https://git-scm.com |
| `Permission denied` | Cek username/password GitHub |
| `Repository not found` | Pastikan repository sudah dibuat di GitHub |
| `Authentication failed` | Setup SSH key atau gunakan personal access token |

Happy coding! 🎉
