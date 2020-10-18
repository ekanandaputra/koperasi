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
import com.example.KoperasiUser.Adapter.AdapterPeriode
import com.example.KoperasiUser.Periode
import com.example.KoperasiUser.R
import com.example.KoperasiUser.Server
import kotlinx.android.synthetic.main.activity_list_angsuran.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ListPeriodeActivity : AppCompatActivity() {

    var arrayList = ArrayList<Periode>()
    var id_user = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_periode)
        rvAngsuran.layoutManager = LinearLayoutManager(applicationContext)
        val bundle = intent.extras
        //id_user = bundle!!.getString("id_user").toString()
        loadperiode()
    }

    private fun loadperiode() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.post(Server.AMBILPERIODE)
            .setPriority(Priority.HIGH)
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
                        Toast.makeText(applicationContext,"Tidak Ditemukan Data", Toast.LENGTH_SHORT).show()
                    }
                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray?.optJSONObject(i)
                        println(jsonObject)
                        arrayList.add(
                            Periode(jsonObject.getString("id"),
                                jsonObject.getString("periode_awal"),
                                jsonObject.getString("periode_akhir"),
                                id_user
                            )
                        )
                        if(jsonArray?.length() - 1 == i){
                            loading.dismiss()
                            val adapter = AdapterPeriode(applicationContext ,arrayList)
                            adapter.notifyDataSetChanged()
                            rvAngsuran.adapter = adapter
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

