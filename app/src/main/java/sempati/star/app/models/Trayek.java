package sempati.star.app.models;

public class Trayek {
    int tujuanAgenId;
    int hargaTrayek;
    int jadwalHarga;
    int perubahanHarga;

    public int getHargaAwal() {
        return hargaAwal;
    }

    public void setHargaAwal(int hargaAwal) {
        this.hargaAwal = hargaAwal;
    }

    int hargaAwal;
    String status;
    int id;
    String nama_trayek, nama_laporan;
    TblKeberangkatan tblKeberangkatan;
    TblTrayek tblTrayek;

    public int getJadwalHarga() {
        return jadwalHarga;
    }

    public void setJadwalHarga(int jadwalHarga) {
        this.jadwalHarga = jadwalHarga;
    }

    public int getPerubahanHarga() {
        return perubahanHarga;
    }

    public void setPerubahanHarga(int perubahanHarga) {
        this.perubahanHarga = perubahanHarga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Trayek(int tujuanAgenId, int hargaTrayek, int id, String nama_trayek, String nama_laporan, TblKeberangkatan tblKeberangkatan, TblTrayek tblTrayek) {
        this.tujuanAgenId = tujuanAgenId;
        this.hargaTrayek = hargaTrayek;
        this.id = id;
        this.nama_trayek = nama_trayek;
        this.nama_laporan = nama_laporan;
        this.tblKeberangkatan = tblKeberangkatan;
        this.tblTrayek = tblTrayek;
    }

    public String getNama_trayek() {
        return nama_trayek;
    }

    public void setNama_trayek(String nama_trayek) {
        this.nama_trayek = nama_trayek;
    }

    public String getNama_laporan() {
        return nama_laporan;
    }

    public void setNama_laporan(String nama_laporan) {
        this.nama_laporan = nama_laporan;
    }

    public Trayek(int tujuanAgenId, int hargaTrayek, int id, TblKeberangkatan tblKeberangkatan, TblTrayek tblTrayek) {
        this.tujuanAgenId = tujuanAgenId;
        this.hargaTrayek = hargaTrayek;
        this.id = id;
        this.tblKeberangkatan = tblKeberangkatan;
        this.tblTrayek = tblTrayek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTujuanAgenId() {
        return tujuanAgenId;
    }

    public void setTujuanAgenId(int tujuanAgenId) {
        this.tujuanAgenId = tujuanAgenId;
    }

    public int getHargaTrayek() {
        return hargaTrayek;
    }

    public void setHargaTrayek(int hargaTrayek) {
        this.hargaTrayek = hargaTrayek;
    }

    public TblKeberangkatan getTblKeberangkatan() {
        return tblKeberangkatan;
    }

    public void setTblKeberangkatan(TblKeberangkatan tblKeberangkatan) {
        this.tblKeberangkatan = tblKeberangkatan;
    }

    public TblTrayek getTblTrayek() {
        return tblTrayek;
    }

    public void setTblTrayek(TblTrayek tblTrayek) {
        this.tblTrayek = tblTrayek;
    }

    public Trayek() {
    }

    public Trayek(int tujuanAgenId, int hargaTrayek, TblKeberangkatan tblKeberangkatan, TblTrayek tblTrayek) {
        this.tujuanAgenId = tujuanAgenId;
        this.hargaTrayek = hargaTrayek;
        this.tblKeberangkatan = tblKeberangkatan;
        this.tblTrayek = tblTrayek;
    }
}
