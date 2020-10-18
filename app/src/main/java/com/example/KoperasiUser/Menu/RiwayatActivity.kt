package com.example.KoperasiUser.Menu

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.KoperasiUser.R
import com.example.KoperasiUser.Server
import com.example.kotlinnotifikasi.DataStorage
import kotlinx.android.synthetic.main.activity_riwayat.*
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

class RiwayatActivity : AppCompatActivity() {

    var id_user = ""
    var id_periode = ""
    var periode = ""
    internal var dataStorage: DataStorage = DataStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        val bundle = intent.extras
        id_user = dataStorage.getId(applicationContext)
        id_periode = bundle!!.getString("id_periode").toString()
        periode = bundle!!.getString("periode").toString()
        tvPeriode.text = periode
        println(id_user)
        println(id_periode)
        loadinfo()
    }

    private fun loadinfo(){
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post(Server.AMBILRIWAYAT)
            .addBodyParameter("id_user", id_user)
            .addBodyParameter("id_periode", id_periode)
            .setPriority(Priority.HIGH)
            .getResponseOnlyFromNetwork()
            .doNotCacheResponse()
            .setMaxAgeCacheControl(0, TimeUnit.SECONDS)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val jsonArray = response?.optJSONArray("result")
                    loading.dismiss()
                    println(response)
                    val jsonObject = jsonArray?.optJSONObject(0)
                    tvSimpananWajib.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("simpanan_wajib")))).toString()
                    tvTabungan.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("tabungan")))).toString()
                    tvJasaTabungan.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("jasa_tabungan")))).toString()
                    tvPinjaman.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("pinjaman")))).toString()
                    tvJasaPinjaman.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("jasa_pinjaman")))).toString()
                    tvShu.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("shu")))).toString()
                    tvKas.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("kas")))).toString()
                    tvTotal.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("total")))).toString()
                    tvKeuntungan.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("keuntungan")))).toString()
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
