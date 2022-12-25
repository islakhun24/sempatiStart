package sempati.star.app.models;


public class TblTrayek {
    String namaTrayek;
    String namaLaporan;
    String jamBerangkat;
    Agen asal;
    Agen tujuan;

    public TblTrayek(String namaTrayek, String namaLaporan, String jamBerangkat, Agen asal, Agen tujuan, int id) {
        this.namaTrayek = namaTrayek;
        this.namaLaporan = namaLaporan;
        this.jamBerangkat = jamBerangkat;
        this.asal = asal;
        this.tujuan = tujuan;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public String getNamaLaporan() {
        return namaLaporan;
    }

    public void setNamaLaporan(String namaLaporan) {
        this.namaLaporan = namaLaporan;
    }

    public TblTrayek(String namaTrayek, String namaLaporan, String jamBerangkat, Agen asal, Agen tujuan) {
        this.namaTrayek = namaTrayek;
        this.namaLaporan = namaLaporan;
        this.jamBerangkat = jamBerangkat;
        this.asal = asal;
        this.tujuan = tujuan;
    }



    public Agen getAsal() {
        return asal;
    }

    public void setAsal(Agen asal) {
        this.asal = asal;
    }

    public Agen getTujuan() {
        return tujuan;
    }

    public void setTujuan(Agen tujuan) {
        this.tujuan = tujuan;
    }

    public TblTrayek() {
    }

    public TblTrayek(String namaTrayek, String jamBerangkat, Agen asal, Agen tujuan) {
        this.namaTrayek = namaTrayek;
        this.jamBerangkat = jamBerangkat;
        this.asal = asal;
        this.tujuan = tujuan;
    }

    public String getNamaTrayek() {
        return namaTrayek;
    }

    public void setNamaTrayek(String namaTrayek) {
        this.namaTrayek = namaTrayek;
    }

    public String getJamBerangkat() {
        return jamBerangkat;
    }

    public void setJamBerangkat(String jamBerangkat) {
        this.jamBerangkat = jamBerangkat;
    }
}

