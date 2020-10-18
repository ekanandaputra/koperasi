package com.example.KoperasiUser

data class Notifikasi (val id:String?, val id_user:String?, val judul:String?, val deskripsi:String? )
data class Angsuran (val id:String?, val id_user:String?, val id_peminjaman:String?, val status:String?, val tanggal_peminjaman:String?, val tanggal_jatuhtempo:String?, val tanggal_pembayaran:String? )
data class Periode (val id:String?, val periode_awal:String?, val periode_akhir:String?, val id_user:String? )
data class RiwayatTabungan (val id:String?, val id_user:String?, val tanggal:String?, val nominal:String? )