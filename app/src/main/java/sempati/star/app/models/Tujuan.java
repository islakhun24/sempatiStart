package sempati.star.app.models;

public class Tujuan {
    private float id;
    private String nama_agen;
    private String kode_agen;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getNama_agen() {
        return nama_agen;
    }

    public String getKode_agen() {
        return kode_agen;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setNama_agen(String nama_agen) {
        this.nama_agen = nama_agen;
    }

    public void setKode_agen(String kode_agen) {
        this.kode_agen = kode_agen;
    }
}
