package sempati.star.app.models;

public class Kelas_armada {
    private float id;
    private String nama_kelas;
    private float jenis_seat;
    private String jumlah_seat;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public float getJenis_seat() {
        return jenis_seat;
    }

    public String getJumlah_seat() {
        return jumlah_seat;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public void setJenis_seat(float jenis_seat) {
        this.jenis_seat = jenis_seat;
    }

    public void setJumlah_seat(String jumlah_seat) {
        this.jumlah_seat = jumlah_seat;
    }
}
