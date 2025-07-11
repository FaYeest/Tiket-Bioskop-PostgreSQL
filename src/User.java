public abstract class User {
    protected String nama;

    public User(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public abstract void menu();
}
