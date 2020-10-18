package com.example.KoperasiUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.example.KoperasiUser.Menu.*
import com.example.kotlinnotifikasi.DataStorage
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    internal var dataStorage: DataStorage = DataStorage()
    private var nama = "nama"
    private var username = "username"
    var jenis = arrayOf("2", "3", "4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        nama = dataStorage.getNama(applicationContext)
        username = dataStorage.getUsername(applicationContext)

        tvHome.text = "Selamat Datang " + nama;

        chat.setOnClickListener {
            masuk_chat_room(username);
        }

        peminjaman.setOnClickListener {
            val intent = Intent(applicationContext, PeminjamanActivity::class.java)
            startActivity(intent)
        }

        tabungan.setOnClickListener {
            val intent = Intent(applicationContext, TabunganActivity::class.java)
            startActivity(intent)
        }

        angsuran.setOnClickListener {
            val intent = Intent(applicationContext, ListAngsuranActivity::class.java)
            startActivity(intent)
        }

        notifikasi.setOnClickListener {
            val intent = Intent(applicationContext, NotifikasiActivity::class.java)
            startActivity(intent)
        }

        profile.setOnClickListener {
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }



    }


    private fun masuk_chat_room(nama: String) {
        CometChat.login(nama, getString(R.string.apiKey), object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {
                val intent = Intent(applicationContext, MessagesActivity::class.java)
                startActivity(intent)
            }
            override fun onError(e: CometChatException) {
                Toast.makeText(applicationContext, "Error or username doesn't exist.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
