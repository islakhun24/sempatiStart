package sempati.star.app.models;

public class TbTrayek {
    private float id;
    private String nama_trayek;
    private String nama_laporan;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getNama_trayek() {
        return nama_trayek;
    }

    public String getNama_laporan() {
        return nama_laporan;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setNama_trayek(String nama_trayek) {
        this.nama_trayek = nama_trayek;
    }

    public void setNama_laporan(String nama_laporan) {
        this.nama_laporan = nama_laporan;
    }
}
