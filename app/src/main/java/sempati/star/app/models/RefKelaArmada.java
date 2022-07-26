package sempati.star.app.models;

public class RefKelaArmada{
    String namaKelas;
    String jumlahSeat;
    int id;

    public int getId() {
        return id;
    }

    public RefKelaArmada(String namaKelas, String jumlahSeat, int id) {
        this.namaKelas = namaKelas;
        this.jumlahSeat = jumlahSeat;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RefKelaArmada() {
    }

    public RefKelaArmada(String namaKelas, String jumlahSeat) {
        this.namaKelas = namaKelas;
        this.jumlahSeat = jumlahSeat;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getJumlahSeat() {
        return jumlahSeat;
    }

    public void setJumlahSeat(String jumlahSeat) {
        this.jumlahSeat = jumlahSeat;
    }
}