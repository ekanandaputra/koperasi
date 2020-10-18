package com.example.KoperasiUser.Menu

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.KoperasiUser.Adapter.AdapterNotif
import com.example.KoperasiUser.Notifikasi
import com.example.KoperasiUser.R
import com.example.KoperasiUser.Server
import com.example.kotlinnotifikasi.DataStorage
import kotlinx.android.synthetic.main.activity_notifikasi.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class NotifikasiActivity : AppCompatActivity() {

    var arrayList = ArrayList<Notifikasi>()
    internal var dataStorage: DataStorage = DataStorage()
    var id_user = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)

        rvNotif.layoutManager = LinearLayoutManager(applicationContext)
        loadNotif()

    }

    private fun loadNotif(){
        id_user = dataStorage.getId(applicationContext)
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post(Server.NOTIFIKASI)
            .setPriority(Priority.HIGH)
            .addBodyParameter("id_user", id_user)
            .getResponseOnlyFromNetwork()
            .doNotCacheResponse()
            .setMaxAgeCacheControl(0, TimeUnit.SECONDS)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    println(response)
                    arrayList.clear()
                    Log.d("ONRESPONSE","Berhasil menampilkan Data")
                    val jsonArray = response?.optJSONArray("result")
                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Tidak Ditemukan Data Menu", Toast.LENGTH_SHORT).show()
                    }
                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray?.optJSONObject(i)
                        println(jsonObject)
                        arrayList.add(
                            Notifikasi(
                                jsonObject.getString("id"),
                                jsonObject.getString("id_user"),
                                jsonObject.getString("judul"),
                                jsonObject.getString("deskripsi")
                            )
                        )
                        if(jsonArray?.length() - 1 == i){
                            loading.dismiss()
                            val adapter = AdapterNotif(applicationContext ,arrayList)
                            adapter.notifyDataSetChanged()
                            rvNotif.adapter = adapter
                        }
                    }

                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })

    }
}
