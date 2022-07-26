import 'dart:convert';
import 'package:SempatiStar.app/model/kelas_armada.dart';

class Lambung {
  String nama;
  KelasArmada kelasArmada;
  int id;

  Lambung({
  this.nama = "",
  this.kelasArmada,
  this.id = 0,
  });

  Lambung.fromJson(Map<String, dynamic>  map) :
        nama = map['nama']  ?? "",
        kelasArmada = map['kelas_armada'] == null
            ? null
            : KelasArmada.fromJson(map['kelas_armada']),
        id = map['id']  ?? 0;

  Map<String, dynamic> toJson() => {
        'nama': nama,
        'kelas_armada': kelasArmada.toJson(),
        'id': id,
      };

  Lambung copyWith({
    String nama,
    KelasArmada kelasArmada,
    int id,
  }) {
    return Lambung(
      nama: nama ?? this.nama,
      kelasArmada: kelasArmada ?? this.kelasArmada,
      id: id ?? this.id,
    );
  }
}

