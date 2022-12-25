package sempati.star.app.models;

public class TransaksiModel {
    String id, keberangkatan_id, agen_id, qrcode, penumpang_id, penumpang_umum, penumpang_hp, komisi_agen_id, tujuan_id;
    String tiba_id, harga_tiket, harga_potongan, uang_muka, status_bayar, no_kursi, nama_kursi, status, status_migrasi, ket;
    String limit_boking, cr_id, cr_id_pusat, cr_datetime, up_id, up_datetime, up_id_pusat, android_id;
    //asal
    String asal_id, asal_nama_agen, asal_kode_agen;
    //tujuan
    String tujuan_tujian_id, tujuan_nama_agen, tujuan_kode_agen;
    //keberangkatan
    String keberangkatan_tanggal, keberangkatan_jam, keberangkatan_waktu, keberangkatan_armada;
    //lambung
    String lambung_id, lambung_nama;
    //kelas_armada
    String kelas_armada_id, kelas_armada_nama, kelas_armada_jenis_seat, kelas_armada_jumlah_seat;
    //trayek
    String trayek_id, trayek_nama, trayek_nama_laporan;

    public String getKeberangkatan_tanggal() {
        return keberangkatan_tanggal;
    }

    public void setKeberangkatan_tanggal(String keberangkatan_tanggal) {
        this.keberangkatan_tanggal = keberangkatan_tanggal;
    }

    public String getKeberangkatan_jam() {
        return keberangkatan_jam;
    }

    public void setKeberangkatan_jam(String keberangkatan_jam) {
        this.keberangkatan_jam = keberangkatan_jam;
    }

    public String getKeberangkatan_waktu() {
        return keberangkatan_waktu;
    }

    public void setKeberangkatan_waktu(String keberangkatan_waktu) {
        this.keberangkatan_waktu = keberangkatan_waktu;
    }

    public String getKeberangkatan_armada() {
        return keberangkatan_armada;
    }

    public void setKeberangkatan_armada(String keberangkatan_armada) {
        this.keberangkatan_armada = keberangkatan_armada;
    }

    public String getLambung_id() {
        return lambung_id;
    }

    public void setLambung_id(String lambung_id) {
        this.lambung_id = lambung_id;
    }

    public String getLambung_nama() {
        return lambung_nama;
    }

    public void setLambung_nama(String lambung_nama) {
        this.lambung_nama = lambung_nama;
    }

    public String getKelas_armada_id() {
        return kelas_armada_id;
    }

    public void setKelas_armada_id(String kelas_armada_id) {
        this.kelas_armada_id = kelas_armada_id;
    }

    public String getKelas_armada_nama() {
        return kelas_armada_nama;
    }

    public void setKelas_armada_nama(String kelas_armada_nama) {
        this.kelas_armada_nama = kelas_armada_nama;
    }

    public String getKelas_armada_jenis_seat() {
        return kelas_armada_jenis_seat;
    }

    public void setKelas_armada_jenis_seat(String kelas_armada_jenis_seat) {
        this.kelas_armada_jenis_seat = kelas_armada_jenis_seat;
    }

    public String getKelas_armada_jumlah_seat() {
        return kelas_armada_jumlah_seat;
    }

    public void setKelas_armada_jumlah_seat(String kelas_armada_jumlah_seat) {
        this.kelas_armada_jumlah_seat = kelas_armada_jumlah_seat;
    }

    public String getTrayek_id() {
        return trayek_id;
    }

    public void setTrayek_id(String trayek_id) {
        this.trayek_id = trayek_id;
    }

    public String getTrayek_nama() {
        return trayek_nama;
    }

    public void setTrayek_nama(String trayek_nama) {
        this.trayek_nama = trayek_nama;
    }

    public String getTrayek_nama_laporan() {
        return trayek_nama_laporan;
    }

    public void setTrayek_nama_laporan(String trayek_nama_laporan) {
        this.trayek_nama_laporan = trayek_nama_laporan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeberangkatan_id() {
        return keberangkatan_id;
    }

    public void setKeberangkatan_id(String keberangkatan_id) {
        this.keberangkatan_id = keberangkatan_id;
    }

    public String getAgen_id() {
        return agen_id;
    }

    public void setAgen_id(String agen_id) {
        this.agen_id = agen_id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getPenumpang_id() {
        return penumpang_id;
    }

    public void setPenumpang_id(String penumpang_id) {
        this.penumpang_id = penumpang_id;
    }

    public String getPenumpang_umum() {
        return penumpang_umum;
    }

    public void setPenumpang_umum(String penumpang_umum) {
        this.penumpang_umum = penumpang_umum;
    }

    public String getPenumpang_hp() {
        return penumpang_hp;
    }

    public void setPenumpang_hp(String penumpang_hp) {
        this.penumpang_hp = penumpang_hp;
    }

    public String getKomisi_agen_id() {
        return komisi_agen_id;
    }

    public void setKomisi_agen_id(String komisi_agen_id) {
        this.komisi_agen_id = komisi_agen_id;
    }

    public String getTujuan_id() {
        return tujuan_id;
    }

    public void setTujuan_id(String tujuan_id) {
        this.tujuan_id = tujuan_id;
    }

    public String getTiba_id() {
        return tiba_id;
    }

    public void setTiba_id(String tiba_id) {
        this.tiba_id = tiba_id;
    }

    public String getHarga_tiket() {
        return harga_tiket;
    }

    public void setHarga_tiket(String harga_tiket) {
        this.harga_tiket = harga_tiket;
    }

    public String getHarga_potongan() {
        return harga_potongan;
    }

    public void setHarga_potongan(String harga_potongan) {
        this.harga_potongan = harga_potongan;
    }

    public String getUang_muka() {
        return uang_muka;
    }

    public void setUang_muka(String uang_muka) {
        this.uang_muka = uang_muka;
    }

    public String getStatus_bayar() {
        return status_bayar;
    }

    public void setStatus_bayar(String status_bayar) {
        this.status_bayar = status_bayar;
    }

    public String getNo_kursi() {
        return no_kursi;
    }

    public void setNo_kursi(String no_kursi) {
        this.no_kursi = no_kursi;
    }

    public String getNama_kursi() {
        return nama_kursi;
    }

    public void setNama_kursi(String nama_kursi) {
        this.nama_kursi = nama_kursi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_migrasi() {
        return status_migrasi;
    }

    public void setStatus_migrasi(String status_migrasi) {
        this.status_migrasi = status_migrasi;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getLimit_boking() {
        return limit_boking;
    }

    public void setLimit_boking(String limit_boking) {
        this.limit_boking = limit_boking;
    }

    public String getCr_id() {
        return cr_id;
    }

    public void setCr_id(String cr_id) {
        this.cr_id = cr_id;
    }

    public String getCr_id_pusat() {
        return cr_id_pusat;
    }

    public void setCr_id_pusat(String cr_id_pusat) {
        this.cr_id_pusat = cr_id_pusat;
    }

    public String getCr_datetime() {
        return cr_datetime;
    }

    public void setCr_datetime(String cr_datetime) {
        this.cr_datetime = cr_datetime;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }

    public String getUp_datetime() {
        return up_datetime;
    }

    public void setUp_datetime(String up_datetime) {
        this.up_datetime = up_datetime;
    }

    public String getUp_id_pusat() {
        return up_id_pusat;
    }

    public void setUp_id_pusat(String up_id_pusat) {
        this.up_id_pusat = up_id_pusat;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public String getAsal_id() {
        return asal_id;
    }

    public void setAsal_id(String asal_id) {
        this.asal_id = asal_id;
    }

    public String getAsal_nama_agen() {
        return asal_nama_agen;
    }

    public void setAsal_nama_agen(String asal_nama_agen) {
        this.asal_nama_agen = asal_nama_agen;
    }

    public String getAsal_kode_agen() {
        return asal_kode_agen;
    }

    public void setAsal_kode_agen(String asal_kode_agen) {
        this.asal_kode_agen = asal_kode_agen;
    }

    public String getTujuan_tujian_id() {
        return tujuan_tujian_id;
    }

    public void setTujuan_tujian_id(String tujuan_tujian_id) {
        this.tujuan_tujian_id = tujuan_tujian_id;
    }

    public String getTujuan_nama_agen() {
        return tujuan_nama_agen;
    }

    public void setTujuan_nama_agen(String tujuan_nama_agen) {
        this.tujuan_nama_agen = tujuan_nama_agen;
    }

    public String getTujuan_kode_agen() {
        return tujuan_kode_agen;
    }

    public void setTujuan_kode_agen(String tujuan_kode_agen) {
        this.tujuan_kode_agen = tujuan_kode_agen;
    }
}
