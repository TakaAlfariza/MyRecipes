package comtugas.bintangrestu.myrecipes;

class dataResep {
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

    public String getAsalMakanan() {
        return asalMakanan;
    }

    public void setAsalMakanan(String asalMakanan) {
        this.asalMakanan = asalMakanan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public dataResep(int idResep, String namaMakanan, String asalMakanan, String deskripsi, String imgFile) {
        this.idResep = idResep;
        this.namaMakanan = namaMakanan;
        this.asalMakanan = asalMakanan;
        this.deskripsi = deskripsi;
        this.imgFile = imgFile;
    }

    private int idResep;
    private String namaMakanan;
    private String asalMakanan;
    private String deskripsi;
    private String imgFile;
}
