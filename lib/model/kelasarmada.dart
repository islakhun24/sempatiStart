import 'dart:convert';

class KelasArmada {
  int jenisSeat;
  String namaKelas;
  String jumlahSeat;
  int id;

  KelasArmada({
  this.jenisSeat = 0,
  this.namaKelas = "",
  this.jumlahSeat = "",
  this.id = 0,
  });

  KelasArmada.fromJson(Map<String, dynamic>  map) :
        jenisSeat = map['jenis_seat']  ?? 0,
        namaKelas = map['nama_kelas']  ?? "",
        jumlahSeat = map['jumlah_seat']  ?? "",
        id = map['id']  ?? 0;

  Map<String, dynamic> toJson() => {
        'jenis_seat': jenisSeat,
        'nama_kelas': namaKelas,
        'jumlah_seat': jumlahSeat,
        'id': id,
      };

  KelasArmada copyWith({
    int jenisSeat,
    String namaKelas,
    String jumlahSeat,
    int id,
  }) {
    return KelasArmada(
      jenisSeat: jenisSeat ?? this.jenisSeat,
      namaKelas: namaKelas ?? this.namaKelas,
      jumlahSeat: jumlahSeat ?? this.jumlahSeat,
      id: id ?? this.id,
    );
  }
}

