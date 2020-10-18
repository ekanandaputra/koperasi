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
import kotlinx.android.synthetic.main.activity_peminjaman.edNominal
import kotlinx.android.synthetic.main.activity_tambah_saldo.*
import org.json.JSONObject

class TambahSaldoActivity : AppCompatActivity() {

    internal var dataStorage: DataStorage = DataStorage()
    var id_user = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_saldo)

        btKirim.setOnClickListener {
            simpan()
        }
    }

    private fun simpan() {
        id_user = dataStorage.getId(applicationContext)
        val loading = ProgressDialog(this)
        loading.setMessage("Silahkan Tunggu Sebentar")
        loading.show()

        AndroidNetworking.post(Server.SIMPANTABUNGAN)
            .addBodyParameter("nominal",edNominal.text.toString())
            .addBodyParameter("id_user", id_user)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(
                        applicationContext, response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(
                        applicationContext, "Connection Failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
