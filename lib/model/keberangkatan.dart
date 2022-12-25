import 'dart:convert';
import 'package:SempatiStar.app/model/lambung.dart';
import 'package:SempatiStar.app/model/trayek.dart';

class Keberangkatan {
  Lambung lambung;
  String jam;
  String waktu;
  Trayek trayek;
  int id;
  String tanggal;
  Null armada;

  Keberangkatan({
  this.lambung,
  this.jam = "",
  this.waktu = "",
  this.trayek,
  this.id = 0,
  this.tanggal = "",
  this.armada = null,
  });

  Keberangkatan.fromJson(Map<String, dynamic>  map) :
        lambung = map['lambung'] == null
            ? null
            : Lambung.fromJson(map['lambung']),
        jam = map['jam']  ?? "",
        waktu = map['waktu']  ?? "",
        trayek = map['trayek'] == null
            ? null
            : Trayek.fromJson(map['trayek']),
        id = map['id']  ?? 0,
        tanggal = map['tanggal']  ?? "",

  Map<String, dynamic> toJson() => {
        'lambung': lambung.toJson(),
        'jam': jam,
        'waktu': waktu,
        'trayek': trayek.toJson(),
        'id': id,
        'tanggal': tanggal,
        'armada': armada,
      };

  Keberangkatan copyWith({
    Lambung lambung,
    String jam,
    String waktu,
    Trayek trayek,
    int id,
    String tanggal,
    Null armada,
  }) {
    return Keberangkatan(
      lambung: lambung ?? this.lambung,
      jam: jam ?? this.jam,
      waktu: waktu ?? this.waktu,
      trayek: trayek ?? this.trayek,
      id: id ?? this.id,
      tanggal: tanggal ?? this.tanggal,
      armada: armada ?? this.armada,
    );
  }
}

