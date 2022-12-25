package sempati.star.app.models;

public class Keberangkatan {
    private float id;
    private String tanggal;
    private String jam;
    private String waktu;
    Lambung LambungObject;
    TbTrayek TrayekObject;
    private String armada = null;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    public String getWaktu() {
        return waktu;
    }

    public Lambung getLambung() {
        return LambungObject;
    }

    public TbTrayek getTrayek() {
        return TrayekObject;
    }

    public String getArmada() {
        return armada;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public void setLambung(Lambung lambungObject) {
        this.LambungObject = lambungObject;
    }

    public void setTrayek(TbTrayek trayekObject) {
        this.TrayekObject = trayekObject;
    }

    public void setArmada(String armada) {
        this.armada = armada;
    }
}
