package comtugas.bintangrestu.myrecipes;

class dataFavorite {
    public int getIdFavorite() {
        return idFavorite;
    }

    public void setIdFavorite(int idFavorite) {
        this.idFavorite = idFavorite;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public int getIdResep() {
        return idResep;
    }

    public void setIdResep(int idResep) {
        this.idResep = idResep;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public dataFavorite(int idFavorite, int idPengguna, int idResep, String namaMakanan, String imgFile) {
        this.idFavorite = idFavorite;
        this.idPengguna = idPengguna;
        this.idResep = idResep;
        this.namaMakanan = namaMakanan;
        this.imgFile = imgFile;
    }

    int idFavorite;
    int idPengguna;
    int idResep;
    String namaMakanan;
    String imgFile;
}
