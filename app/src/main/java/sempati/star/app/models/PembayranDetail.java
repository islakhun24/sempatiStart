package sempati.star.app.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PembayranDetail {
    private float id;
    Keberangkatan KeberangkatanObject;
    Asal AsalObject;
    Tujuan TujuanObject;
    String no_kursi;
    String penumpang_umum, penumpang_hp, harga_potongan;
    String keberangkatan_id;
    String agen_id;
    String penumpang_id;
    String komisi_agen_id;
    String tiba_id;
    String harga_tiket;
    String lambung_id;
    String status;
    String status_migrasi;
    String qrcode;
    String ket;
    String limit_boking;
    String tickes;
    String cr_id;
    String cr_id_pusat;
    String cr_datetime;
    String up_id;
    String up_datetime;
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

    public String getPenumpang_id() {
        return penumpang_id;
    }

    public void setPenumpang_id(String penumpang_id) {
        this.penumpang_id = penumpang_id;
    }

    public String getKomisi_agen_id() {
        return komisi_agen_id;
    }

    public void setKomisi_agen_id(String komisi_agen_id) {
        this.komisi_agen_id = komisi_agen_id;
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

    public String getLambung_id() {
        return lambung_id;
    }

    public void setLambung_id(String lambung_id) {
        this.lambung_id = lambung_id;
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

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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

    public String getTickes() {
        return tickes;
    }

    public void setTickes(String tickes) {
        this.tickes = tickes;
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

    // Getter Methods

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

    public String getHarga_potongan() {
        return harga_potongan;
    }

    public void setHarga_potongan(String harga_potongan) {
        this.harga_potongan = harga_potongan;
    }

    public float getId() {
        return id;
    }

    public Keberangkatan getKeberangkatan() {
        return KeberangkatanObject;
    }

    public Asal getAsal() {
        return AsalObject;
    }

    public Tujuan getTujuan() {
        return TujuanObject;
    }

    public Keberangkatan getKeberangkatanObject() {
        return KeberangkatanObject;
    }

    public void setKeberangkatanObject(Keberangkatan keberangkatanObject) {
        KeberangkatanObject = keberangkatanObject;
    }

    public Asal getAsalObject() {
        return AsalObject;
    }

    public void setAsalObject(Asal asalObject) {
        AsalObject = asalObject;
    }

    public Tujuan getTujuanObject() {
        return TujuanObject;
    }

    public void setTujuanObject(Tujuan tujuanObject) {
        TujuanObject = tujuanObject;
    }

    public String getNo_kursi() {
        return no_kursi;
    }

    public void setNo_kursi(String no_kursi) {
        this.no_kursi = no_kursi;
    }
// Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setKeberangkatan(Keberangkatan keberangkatanObject) {
        this.KeberangkatanObject = keberangkatanObject;
    }

    public void setAsal(Asal asalObject) {
        this.AsalObject = asalObject;
    }

    public void setTujuan(Tujuan tujuanObject) {
        this.TujuanObject = tujuanObject;
    }


}