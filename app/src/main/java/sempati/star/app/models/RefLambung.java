package sempati.star.app.models;

public class RefLambung {
    String nama;
    int id;
    RefKelaArmada refKelaArmada;

    public RefLambung() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RefLambung(String nama, int id, RefKelaArmada refKelaArmada) {
        this.nama = nama;
        this.id = id;
        this.refKelaArmada = refKelaArmada;
    }

    public RefLambung(String nama, RefKelaArmada refKelaArmada) {
        this.nama = nama;
        this.refKelaArmada = refKelaArmada;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public RefKelaArmada getRefKelaArmada() {
        return refKelaArmada;
    }

    public void setRefKelaArmada(RefKelaArmada refKelaArmada) {
        this.refKelaArmada = refKelaArmada;
    }
}

