package com.example.KoperasiUser

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.kotlinnotifikasi.DataStorage
import com.jacksonandroidnetworking.JacksonParserFactory
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private val TAG_SUCCESS = "success"
    private val TAG_MESSAGE = "message"
    private val TAG_ID = "id"
    private val TAG_NAMA = "nama"
    private val TAG_USERNAME = "username"
    internal var dataStorage: DataStorage = DataStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        AndroidNetworking.setParserFactory(JacksonParserFactory())
        btLogin.setOnClickListener {
            prosesLogin()
        }

        tvRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }

        tvSimulasi.setOnClickListener {
            val intent = Intent(applicationContext, SimulasiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun prosesLogin(){
        val loading = ProgressDialog(this)
        loading.setMessage("Proses Login...")
        loading.show()
        AndroidNetworking.post(Server.LOGIN)
            .addBodyParameter("username",edUser.editableText.toString())
            .addBodyParameter("password",edPass.editableText.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.d("ONRESPONSE","Berhasil menampilkan Data")
                    loading.dismiss()
                    println(response)
                    if (response?.get(TAG_SUCCESS).toString().equals("1")){
                        val bundle = Bundle()
                        bundle.putString("success", response?.get(TAG_SUCCESS).toString())
                        bundle.putString("id", response?.get(TAG_ID).toString())
                        bundle.putString("nama", response?.get(TAG_NAMA).toString())
                        dataStorage.saveNama(applicationContext, response?.get(TAG_NAMA).toString())
                        dataStorage.saveId(applicationContext, response?.get(TAG_ID).toString())
                        dataStorage.saveUsername(applicationContext, response?.get(TAG_USERNAME).toString())
                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(applicationContext, response?.get(TAG_MESSAGE).toString(),
                            Toast.LENGTH_LONG).show()
                    }
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    println(anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",
                        Toast.LENGTH_SHORT).show()
                }
            })
    }
}
