import 'dart:convert';

class Trayek {
  String namaTrayek;
  String namaLaporan;
  int id;

  Trayek({
  this.namaTrayek = "",
  this.namaLaporan = "",
  this.id = 0,
  });

  Trayek.fromJson(Map<String, dynamic>  map) :
        namaTrayek = map['nama_trayek']  ?? "",
        namaLaporan = map['nama_laporan']  ?? "",
        id = map['id']  ?? 0;

  Map<String, dynamic> toJson() => {
        'nama_trayek': namaTrayek,
        'nama_laporan': namaLaporan,
        'id': id,
      };

  Trayek copyWith({
    String namaTrayek,
    String namaLaporan,
    int id,
  }) {
    return Trayek(
      namaTrayek: namaTrayek ?? this.namaTrayek,
      namaLaporan: namaLaporan ?? this.namaLaporan,
      id: id ?? this.id,
    );
  }
}

