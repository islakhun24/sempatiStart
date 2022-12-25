package sempati.star.app.models;

import java.util.ArrayList;

public class Kursi {
    Detail detail;
    ArrayList<Seat> seat;

    public Kursi() {
    }

    public Kursi(Detail detail, ArrayList<Seat> seat) {
        this.detail = detail;
        this.seat = seat;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public ArrayList<Seat> getSeat() {
        return seat;
    }

    public void setSeat(ArrayList<Seat> seat) {
        this.seat = seat;
    }

    public static class Detail {
        int id;
        String jam;
        String tanggal;
        RefLambung refLambung;
        TblTrayek tblTrayek;

        public Detail() {
        }

        public Detail(int id, String jam, String tanggal, RefLambung refLambung, TblTrayek tblTrayek) {
            this.id = id;
            this.jam = jam;
            this.tanggal = tanggal;
            this.refLambung = refLambung;
            this.tblTrayek = tblTrayek;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJam() {
            return jam;
        }

        public void setJam(String jam) {
            this.jam = jam;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public RefLambung getRefLambung() {
            return refLambung;
        }

        public void setRefLambung(RefLambung refLambung) {
            this.refLambung = refLambung;
        }

        public TblTrayek getTblTrayek() {
            return tblTrayek;
        }

        public void setTblTrayek(TblTrayek tblTrayek) {
            this.tblTrayek = tblTrayek;
        }
    }

    public static class Seat {
        int id;
        int kelasArmadaId;
        String namaDenah;
        int baris;
        String kolom1;
        String namaKursi1;
        String kolom2;
        String namaKursi2;
        String kolom3;
        String namaKursi3;
        String kolom4;
        String namaKursi4;
        String kolom5;
        String namaKursi5;
        IsColumn isKolumn1;
        IsColumn isKolumn2;
        IsColumn isKolumn3;
        IsColumn isKolumn4;
        IsColumn isKolumn5;

        public Seat() {
        }

        public Seat(int id, int kelasArmadaId, String namaDenah, int baris, String kolom1, String namaKursi1, String kolom2, String namaKursi2, String kolom3, String namaKursi3, String kolom4, String namaKursi4, String kolom5, String namaKursi5, IsColumn isKolumn1, IsColumn isKolumn2, IsColumn isKolumn3, IsColumn isKolumn4, IsColumn isKolumn5) {
            this.id = id;
            this.kelasArmadaId = kelasArmadaId;
            this.namaDenah = namaDenah;
            this.baris = baris;
            this.kolom1 = kolom1;
            this.namaKursi1 = namaKursi1;
            this.kolom2 = kolom2;
            this.namaKursi2 = namaKursi2;
            this.kolom3 = kolom3;
            this.namaKursi3 = namaKursi3;
            this.kolom4 = kolom4;
            this.namaKursi4 = namaKursi4;
            this.kolom5 = kolom5;
            this.namaKursi5 = namaKursi5;
            this.isKolumn1 = isKolumn1;
            this.isKolumn2 = isKolumn2;
            this.isKolumn3 = isKolumn3;
            this.isKolumn4 = isKolumn4;
            this.isKolumn5 = isKolumn5;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getKelasArmadaId() {
            return kelasArmadaId;
        }

        public void setKelasArmadaId(int kelasArmadaId) {
            this.kelasArmadaId = kelasArmadaId;
        }

        public String getNamaDenah() {
            return namaDenah;
        }

        public void setNamaDenah(String namaDenah) {
            this.namaDenah = namaDenah;
        }

        public int getBaris() {
            return baris;
        }

        public void setBaris(int baris) {
            this.baris = baris;
        }

        public String getKolom1() {
            return kolom1;
        }

        public void setKolom1(String kolom1) {
            this.kolom1 = kolom1;
        }

        public String getNamaKursi1() {
            return namaKursi1;
        }

        public void setNamaKursi1(String namaKursi1) {
            this.namaKursi1 = namaKursi1;
        }

        public String getKolom2() {
            return kolom2;
        }

        public void setKolom2(String kolom2) {
            this.kolom2 = kolom2;
        }

        public String getNamaKursi2() {
            return namaKursi2;
        }

        public void setNamaKursi2(String namaKursi2) {
            this.namaKursi2 = namaKursi2;
        }

        public String getKolom3() {
            return kolom3;
        }

        public void setKolom3(String kolom3) {
            this.kolom3 = kolom3;
        }

        public String getNamaKursi3() {
            return namaKursi3;
        }

        public void setNamaKursi3(String namaKursi3) {
            this.namaKursi3 = namaKursi3;
        }

        public String getKolom4() {
            return kolom4;
        }

        public void setKolom4(String kolom4) {
            this.kolom4 = kolom4;
        }

        public String getNamaKursi4() {
            return namaKursi4;
        }

        public void setNamaKursi4(String namaKursi4) {
            this.namaKursi4 = namaKursi4;
        }

        public String getKolom5() {
            return kolom5;
        }

        public void setKolom5(String kolom5) {
            this.kolom5 = kolom5;
        }

        public String getNamaKursi5() {
            return namaKursi5;
        }

        public void setNamaKursi5(String namaKursi5) {
            this.namaKursi5 = namaKursi5;
        }

        public IsColumn getIsKolumn1() {
            return isKolumn1;
        }

        public void setIsKolumn1(IsColumn isKolumn1) {
            this.isKolumn1 = isKolumn1;
        }

        public IsColumn getIsKolumn2() {
            return isKolumn2;
        }

        public void setIsKolumn2(IsColumn isKolumn2) {
            this.isKolumn2 = isKolumn2;
        }

        public IsColumn getIsKolumn3() {
            return isKolumn3;
        }

        public void setIsKolumn3(IsColumn isKolumn3) {
            this.isKolumn3 = isKolumn3;
        }

        public IsColumn getIsKolumn4() {
            return isKolumn4;
        }

        public void setIsKolumn4(IsColumn isKolumn4) {
            this.isKolumn4 = isKolumn4;
        }

        public IsColumn getIsKolumn5() {
            return isKolumn5;
        }

        public void setIsKolumn5(IsColumn isKolumn5) {
            this.isKolumn5 = isKolumn5;
        }
    }
}
