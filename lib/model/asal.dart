import 'dart:convert';

class Asal {
  String kodeAgen;
  String namaAgen;
  int id;

  Asal({
  this.kodeAgen = "",
  this.namaAgen = "",
  this.id = 0,
  });

  Asal.fromJson(Map<String, dynamic>  map) :
        kodeAgen = map['kode_agen']  ?? "",
        namaAgen = map['nama_agen']  ?? "",
        id = map['id']  ?? 0;

  Map<String, dynamic> toJson() => {
        'kode_agen': kodeAgen,
        'nama_agen': namaAgen,
        'id': id,
      };

  Asal copyWith({
    String kodeAgen,
    String namaAgen,
    int id,
  }) {
    return Asal(
      kodeAgen: kodeAgen ?? this.kodeAgen,
      namaAgen: namaAgen ?? this.namaAgen,
      id: id ?? this.id,
    );
  }
}

