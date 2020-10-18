package com.example.KoperasiUser

class Server {
    companion object {
        private val SERVER = "http://enjoycoding.my.id/koperasi/"
        val LOGIN = SERVER +"proses_login.php"
        val REGISTER = SERVER +"proses_register.php"
        val NOTIFIKASI = SERVER +"ambil_notifikasi.php"
        val SIMPANPEMINJAMAN = SERVER +"simpan_peminjaman.php"
        val SIMPANTABUNGAN = SERVER +"simpan_tabungan.php"
        val AMBILTABUNGAN = SERVER +"ambil_tabungan.php"
        val AMBILANGSURAN = SERVER +"ambil_angsuran.php"
        val DETAILUSER = SERVER +"ambil_detail_user.php"
        val AMBILINFO = SERVER +"ambil_info.php"
        val UPLOADKTP = SERVER +"upload_ktp.php"
        val AMBILPERIODE = SERVER +"ambil_periode.php"
        val AMBILRIWAYAT = SERVER +"ambil_riwayat.php"
        val AMBILRIWAYATTABUNGAN = SERVER +"ambil_riwayat_tabungan.php"
    }
}