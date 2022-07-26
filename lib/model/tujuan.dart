import 'dart:convert';

class Tujuan {
  String kodeAgen;
  String namaAgen;
  int id;

  Tujuan({
  this.kodeAgen = "",
  this.namaAgen = "",
  this.id = 0,
  });

  Tujuan.fromJson(Map<String, dynamic>  map) :
        kodeAgen = map['kode_agen']  ?? "",
        namaAgen = map['nama_agen']  ?? "",
        id = map['id']  ?? 0;

  Map<String, dynamic> toJson() => {
        'kode_agen': kodeAgen,
        'nama_agen': namaAgen,
        'id': id,
      };

  Tujuan copyWith({
    String kodeAgen,
    String namaAgen,
    int id,
  }) {
    return Tujuan(
      kodeAgen: kodeAgen ?? this.kodeAgen,
      namaAgen: namaAgen ?? this.namaAgen,
      id: id ?? this.id,
    );
  }
}

