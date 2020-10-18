package com.example.KoperasiUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_simulasi.*
import java.text.NumberFormat
import java.util.*

class SimulasiActivity : AppCompatActivity() {

    var jumlah = arrayOf("2", "3", "4")
    var jumlah_cicilan = ""
    var biaya_admin = 0
    var total = 0
    var cicilan = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulasi)

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
    }
}
