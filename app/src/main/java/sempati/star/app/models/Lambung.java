package sempati.star.app.models;

public class Lambung {
    private float id;
    private String nama;
    Kelas_armada Kelas_armadaObject;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public Kelas_armada getKelas_armada() {
        return Kelas_armadaObject;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKelas_armada(Kelas_armada kelas_armadaObject) {
        this.Kelas_armadaObject = kelas_armadaObject;
    }
}
