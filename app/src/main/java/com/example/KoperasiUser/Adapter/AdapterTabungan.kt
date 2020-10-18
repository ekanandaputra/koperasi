package com.example.KoperasiUser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.KoperasiUser.Angsuran
import com.example.KoperasiUser.R
import com.example.KoperasiUser.RiwayatTabungan
import kotlinx.android.synthetic.main.layout_angsuran.view.*
import kotlinx.android.synthetic.main.layout_riwayat_tabungan.view.*

class AdapterTabungan(private val context: Context, private val arrayList: ArrayList<RiwayatTabungan>) : RecyclerView.Adapter<AdapterTabungan.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.layout_riwayat_tabungan,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.tvNominal.text = arrayList?.get(position)?.nominal
        holder.view.tvTanggal.text = arrayList?.get(position)?.tanggal
//        holder.itemView.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("no_kamar", arrayList!!.get(position)?.no_kamar)
//            val intent = Intent(context, KontrolActivity::class.java)
//            intent.putExtras(bundle)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent)
//        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}