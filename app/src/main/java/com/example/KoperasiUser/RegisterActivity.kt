package com.example.KoperasiUser

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val GALLERY = 1
    private val CAMERA = 2
    var foto: Bitmap? = null
    var myCalendar = Calendar.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edTanggalLahir.setOnClickListener {
            tanggal(edTanggalLahir)
        }

        btRegister.setOnClickListener {
            prosesRegister()
        }

        tvLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        btUploadKtp.setOnClickListener {
            showPictureDialog()
        }
    }

    private fun prosesRegister(){
        val loading = ProgressDialog(this)
        loading.setMessage("Proses Registerasi...")
        loading.show()

        AndroidNetworking.post(Server.REGISTER)
            .addBodyParameter("nama",edNama.editableText.toString())
            .addBodyParameter("hp",edHp.editableText.toString())
            .addBodyParameter("tanggal_lahir",edTanggalLahir.text.toString())
            .addBodyParameter("alamat",edAlamat.editableText.toString())
            .addBodyParameter("pekerjaan",edPekerjaan.editableText.toString())
            .addBodyParameter("username",edUsername.editableText.toString())
            .addBodyParameter("password",edPass.editableText.toString())
            .setPriority(Priority.HIGH)
            .responseOnlyFromNetwork
            .doNotCacheResponse()
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    uploadktp()
                    Toast.makeText(applicationContext,response?.getString("message"),
                        Toast.LENGTH_SHORT).show()
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",
                        Toast.LENGTH_SHORT).show()                    }
            })
    }

    private fun uploadktp(){
        val loading = ProgressDialog(this)
        loading.setMessage("Proses Registerasi...")
        loading.show()

        AndroidNetworking.post(Server.UPLOADKTP)
            .addBodyParameter("nama",edNama.editableText.toString())
            .addBodyParameter("ktp", foto?.let { encodeTobase64(it) })
            .setPriority(Priority.HIGH)
            .responseOnlyFromNetwork
            .doNotCacheResponse()
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),
                        Toast.LENGTH_SHORT).show()
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",
                        Toast.LENGTH_SHORT).show()                    }
            })
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog?.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Photo Gallery", "Camera")
        pictureDialog?.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> fromGallery()
                1 -> fromCamera()
            }
        }
        pictureDialog?.show()
    }

    private fun fromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun fromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    foto = MediaStore.Images.Media.getBitmap(applicationContext?.contentResolver, contentURI)
                    ivGambar.setImageBitmap(foto)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA) {
            foto = data!!.extras!!.get("data") as Bitmap
            ivGambar.setImageBitmap(foto)
        }
    }

    fun encodeTobase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun tanggal(checkin: TextView?){
        DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val formatTanggal = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(formatTanggal)
                checkin?.setText(sdf.format(myCalendar.getTime()))
            },
            myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}
