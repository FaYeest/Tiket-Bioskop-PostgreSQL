@echo off
echo ===============================================
echo   Git Repository Reset
echo ===============================================
echo.

REM Cek apakah folder .git ada
if not exist ".git" (
    echo [INFO] Tidak ada Git repository yang perlu direset.
    echo Repository belum diinisialisasi.
    pause
    exit /b 0
)

echo [WARNING] Ini akan menghapus semua konfigurasi Git dan history!
echo Apakah Anda yakin ingin mereset Git repository?
echo.
echo 1. Ya, reset Git repository
echo 2. Tidak, batalkan
echo.
set /p choice="Pilihan (1/2): "

if "%choice%"=="2" (
    echo [INFO] Reset dibatalkan.
    pause
    exit /b 0
)

if not "%choice%"=="1" (
    echo [ERROR] Pilihan tidak valid!
    pause
    exit /b 1
)

echo.
echo [INFO] Mereset Git repository...

REM Hapus folder .git
rmdir /s /q ".git" 2>nul

if exist ".git" (
    echo [ERROR] Gagal menghapus folder .git
    echo Coba tutup VS Code atau aplikasi lain yang mungkin menggunakan folder ini.
    pause
    exit /b 1
)

echo [SUCCESS] Git repository berhasil direset!
echo.
echo Status sekarang:
echo - Folder .git telah dihapus
echo - History commit telah dihapus
echo - Remote repository links telah dihapus
echo - File-file project tetap utuh
echo.
echo Untuk setup ulang Git, jalankan:
echo   git_setup.bat
echo.
echo Atau manual:
echo   git init
echo   git add .
echo   git commit -m "Initial commit"
echo.

pause
