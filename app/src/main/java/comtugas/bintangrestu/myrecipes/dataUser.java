package comtugas.bintangrestu.myrecipes;
public class dataUser {
    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }



    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public String getKataSandi() {
        return kataSandi;
    }

    public void setKataSandi(String kataSandi) {
        this.kataSandi = kataSandi;
    }

    private int idPengguna;
    private String namaLengkap;
    private String email;

    public dataUser(int idPengguna, String namaLengkap, String email, String nohp, String namaPengguna, String kataSandi) {
        this.idPengguna = idPengguna;
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.nohp = nohp;
        this.namaPengguna = namaPengguna;
        this.kataSandi = kataSandi;
    }

    private String nohp;
    private String namaPengguna;
    private String kataSandi;

}
