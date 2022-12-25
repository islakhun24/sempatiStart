package sempati.star.app.models;

public class Agen {
    int id;
    String namaAgen;
    String kodeAgn;

    public Agen(int id, String namaAgen, String kodeAgn) {
        this.id = id;
        this.namaAgen = namaAgen;
        this.kodeAgn = kodeAgn;
    }

    public String getKodeAgn() {
        return kodeAgn;
    }

    public void setKodeAgn(String kodeAgn) {
        this.kodeAgn = kodeAgn;
    }

    public Agen() {
    }

    public Agen(int id, String namaAgen) {
        this.id = id;
        this.namaAgen = namaAgen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaAgen() {
        return namaAgen;
    }

    public void setNamaAgen(String namaAgen) {
        this.namaAgen = namaAgen;
    }
}
