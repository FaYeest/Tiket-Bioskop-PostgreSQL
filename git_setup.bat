@echo off
echo ===============================================
echo   Setup Git dan Push ke GitHub
echo ===============================================
echo.

REM Cek apakah Git sudah terinstall
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Git belum terinstall!
    echo Silakan download dan install Git dari: https://git-scm.com/download/win
    pause
    exit /b 1
)

echo [INFO] Git sudah terinstall.
echo.

REM Cek apakah sudah ada .git folder
if exist ".git" (
    echo [INFO] Repository Git sudah ada.
    echo [INFO] Menambahkan perubahan terbaru...
    git add .
    
    echo [INPUT] Masukkan pesan commit:
    set /p commit_message="Commit message: "
    if "%commit_message%"=="" set commit_message="Update project files"
    
    git commit -m "%commit_message%"
    
    echo [INFO] Pushing ke GitHub...
    git push origin main
    
    if %errorlevel% equ 0 (
        echo [SUCCESS] Push berhasil!
    ) else (
        echo [ERROR] Push gagal! Pastikan remote repository sudah ada.
    )
    
    pause
    exit /b 0
)

echo [INFO] Inisialisasi Git repository...

REM Initialize Git
git init

REM Add semua file
echo [INFO] Menambahkan file ke staging...
git add .

REM Commit pertama
echo [INFO] Membuat commit pertama...
git commit -m "Initial commit: Sistem Tiket Bioskop dengan Database PostgreSQL"

echo.
echo ===============================================
echo   Setup Remote Repository
echo ===============================================
echo.
echo Langkah selanjutnya:
echo 1. Buat repository baru di GitHub.com
echo 2. Copy URL repository (contoh: https://github.com/username/repo-name.git)
echo 3. Masukkan URL tersebut di bawah ini
echo.

set /p repo_url="Masukkan URL GitHub repository: "

if "%repo_url%"=="" (
    echo [ERROR] URL repository tidak boleh kosong!
    echo.
    echo Cara manual:
    echo git remote add origin https://github.com/USERNAME/REPO-NAME.git
    echo git branch -M main
    echo git push -u origin main
    pause
    exit /b 1
)

REM Add remote
echo [INFO] Menambahkan remote repository...
git remote add origin %repo_url%

REM Set main branch
git branch -M main

REM Push ke GitHub
echo [INFO] Pushing ke GitHub...
git push -u origin main

if %errorlevel% equ 0 (
    echo.
    echo ===============================================
    echo   SUCCESS! 🎉
    echo ===============================================
    echo Project berhasil di-push ke GitHub!
    echo Repository: %repo_url%
    echo.
    echo Untuk update selanjutnya, gunakan:
    echo   git add .
    echo   git commit -m "Your message"
    echo   git push origin main
    echo.
) else (
    echo.
    echo ===============================================
    echo   ERROR! ❌
    echo ===============================================
    echo Push gagal! Kemungkinan penyebab:
    echo 1. Repository belum dibuat di GitHub
    echo 2. URL repository salah
    echo 3. Tidak ada permission untuk push
    echo.
    echo Solusi manual:
    echo 1. Pastikan repository sudah dibuat di GitHub
    echo 2. Jalankan: git remote -v (untuk cek remote)
    echo 3. Jalankan: git push -u origin main
    echo.
)

pause
