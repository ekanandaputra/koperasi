package com.example.KoperasiUser.Menu

import android.app.ProgressDialog
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_tabungan.*
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TabunganActivity : AppCompatActivity() {

    internal var dataStorage: DataStorage = DataStorage()
    var id_user = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabungan)

        loadtabungan()
        tvTambahSaldo.setOnClickListener {
            val intent = Intent(applicationContext, TambahSaldoActivity::class.java)
            startActivity(intent)
        }
        btRiwayat.setOnClickListener {
            val intent = Intent(applicationContext, ListTabunganActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadtabungan(){
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        id_user = dataStorage.getId(applicationContext)
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post(Server.AMBILTABUNGAN)
            .addBodyParameter("id_user",id_user)
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
                    tvSaldoAnggota.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("saldo")))).toString()
                    tvSimpananWajib.text = (formatRupiah.format(Integer.parseInt(jsonObject!!.getString("simpanan_wajib")))).toString()
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
