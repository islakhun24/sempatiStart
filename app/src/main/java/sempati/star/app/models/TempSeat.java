package sempati.star.app.models;

public class TempSeat {
    String keberangkatanId, noKursi, baris, userId;

    public TempSeat() {
    }

    public TempSeat(String keberangkatanId, String noKursi, String baris, String userId) {
        this.keberangkatanId = keberangkatanId;
        this.noKursi = noKursi;
        this.baris = baris;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeberangkatanId() {
        return keberangkatanId;
    }

    public void setKeberangkatanId(String keberangkatanId) {
        this.keberangkatanId = keberangkatanId;
    }

    public String getNoKursi() {
        return noKursi;
    }

    public void setNoKursi(String noKursi) {
        this.noKursi = noKursi;
    }

    public String getBaris() {
        return baris;
    }

    public void setBaris(String baris) {
        this.baris = baris;
    }
}
