package com.example.KoperasiUser.Menu

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.KoperasiUser.R
import com.example.KoperasiUser.Server
import com.example.kotlinnotifikasi.DataStorage
import kotlinx.android.synthetic.main.activity_peminjaman.*
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*

class PeminjamanActivity : AppCompatActivity() {

    var jumlah = arrayOf("2", "3", "4")
    var jumlah_cicilan = ""
    var biaya_admin = 0
    var total = 0
    var cicilan = 0
    internal var dataStorage: DataStorage = DataStorage()
    var id_user = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peminjaman)

        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)

        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            jumlah // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerCicilan.adapter = adapter
        spinnerCicilan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                jumlah_cicilan = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        btSimulasi.setOnClickListener {
            biaya_admin = Integer.parseInt(edNominal.text.toString()) * 8 / 100
            total = Integer.parseInt(edNominal.text.toString()) + biaya_admin
            cicilan = total / Integer.parseInt(jumlah_cicilan)
            tvNominal.text = (formatRupiah.format(Integer.parseInt(edNominal.text.toString()))).toString()
            tvAdmin.text = (formatRupiah.format(biaya_admin)).toString()
            tvCicilan.text = (formatRupiah.format(cicilan)).toString()
            tvTotal.text = (formatRupiah.format(total)).toString()
        }

        btAjukan.setOnClickListener {
            ajukan()
        }
    }

    private fun ajukan() {
        id_user = dataStorage.getId(applicationContext)
        val loading = ProgressDialog(this)
        loading.setMessage("Silahkan Tunggu Sebentar")
        loading.show()

        AndroidNetworking.post(Server.SIMPANPEMINJAMAN)
            .addBodyParameter("id_user",id_user)
            .addBodyParameter("nominal",edNominal.text.toString())
            .addBodyParameter("admin", biaya_admin.toString())
            .addBodyParameter("jumlah",jumlah_cicilan)
            .addBodyParameter("cicilan",cicilan.toString())
            .addBodyParameter("total",total.toString())
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