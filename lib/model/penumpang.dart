import 'dart:convert';
import 'package:SempatiStar.app/model/asal.dart';
import 'package:SempatiStar.app/model/keberangkatan.dart';
import 'package:SempatiStar.app/model/tujuan.dart';

class Penumpang {
  Asal asal;
  int id;
  Keberangkatan keberangkatan;
  Tujuan tujuan;

  Penumpang({
  this.asal,
  this.id = 0,
  this.keberangkatan,
  this.tujuan,
  });

  Penumpang.fromJson(Map<String, dynamic>  map) :
        asal = map['asal'] == null
            ? null
            : Asal.fromJson(map['asal']),
        id = map['id']  ?? 0,
        keberangkatan = map['keberangkatan'] == null
            ? null
            : Keberangkatan.fromJson(map['keberangkatan']),
        tujuan = map['tujuan'] == null
            ? null
            : Tujuan.fromJson(map['tujuan']);

  Map<String, dynamic> toJson() => {
        'asal': asal.toJson(),
        'id': id,
        'keberangkatan': keberangkatan.toJson(),
        'tujuan': tujuan.toJson(),
      };

  Penumpang copyWith({
    Asal asal,
    int id,
    Keberangkatan keberangkatan,
    Tujuan tujuan,
  }) {
    return Penumpang(
      asal: asal ?? this.asal,
      id: id ?? this.id,
      keberangkatan: keberangkatan ?? this.keberangkatan,
      tujuan: tujuan ?? this.tujuan,
    );
  }
}

