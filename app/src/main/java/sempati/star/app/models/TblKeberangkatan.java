package sempati.star.app.models;

public class TblKeberangkatan {
    int id;
    String waktu;
    String jam;
    RefLambung refLambung;

    public TblKeberangkatan() {
    }

    public TblKeberangkatan(int id, String waktu, String jam, RefLambung refLambung) {
        this.id = id;
        this.waktu = waktu;
        this.jam = jam;
        this.refLambung = refLambung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public RefLambung getRefLambung() {
        return refLambung;
    }

    public void setRefLambung(RefLambung refLambung) {
        this.refLambung = refLambung;
    }
}
