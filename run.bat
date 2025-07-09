@echo off
echo ===============================================
echo   Sistem Tiket Bioskop XXI - Database Version
echo ===============================================
echo.

REM Cek apakah file database.properties ada
if not exist "database.properties" (
    echo [WARNING] File database.properties tidak ditemukan!
    echo Silakan copy database.properties.example ke database.properties
    echo dan sesuaikan konfigurasi database Anda.
    echo.
    pause
    exit /b 1
)

echo [INFO] Kompiling Java files...
javac -cp "lib\postgresql-42.7.7.jar" -d bin src\*.java

if %errorlevel% neq 0 (
    echo [ERROR] Kompilasi gagal!
    pause
    exit /b 1
)

echo [INFO] Kompilasi berhasil!
echo [INFO] Menjalankan aplikasi...
echo.

java -cp "bin;lib\postgresql-42.7.7.jar" Main

echo.
echo [INFO] Aplikasi selesai.
pause
