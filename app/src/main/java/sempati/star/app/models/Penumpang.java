package sempati.star.app.models;

public class Penumpang {
    private float id;
    Keberangkatan KeberangkatanObject;
    Asal AsalObject;
    Tujuan TujuanObject;


    // Getter Methods

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

