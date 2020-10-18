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
import com.example.KoperasiUser.Adapter.AdapterAngsuran
import com.example.KoperasiUser.Adapter.AdapterTabungan
import com.example.KoperasiUser.Angsuran
import com.example.KoperasiUser.R
import com.example.KoperasiUser.RiwayatTabungan
import com.example.KoperasiUser.Server
import com.example.kotlinnotifikasi.DataStorage
import kotlinx.android.synthetic.main.activity_list_angsuran.*
import kotlinx.android.synthetic.main.activity_list_tabungan.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ListTabunganActivity : AppCompatActivity() {

    var arrayList = ArrayList<RiwayatTabungan>()
    internal var dataStorage: DataStorage = DataStorage()
    var id_user = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tabungan)
        rvTabungan.layoutManager = LinearLayoutManager(applicationContext)
        loadtabungan()

    }

    private fun loadtabungan(){
        id_user = dataStorage.getId(applicationContext)
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post(Server.AMBILRIWAYATTABUNGAN)
            .setPriority(Priority.HIGH)
            .getResponseOnlyFromNetwork()
            .addBodyParameter("id_user",id_user)
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
                            RiwayatTabungan(
                                jsonObject.getString("id"),
                                jsonObject.getString("id_user"),
                                jsonObject.getString("tanggal"),
                                jsonObject.getString("nominal")
                            )
                        )
                        if(jsonArray?.length() - 1 == i){
                            loading.dismiss()
                            val adapter = AdapterTabungan(applicationContext ,arrayList)
                            adapter.notifyDataSetChanged()
                            rvTabungan.adapter = adapter
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