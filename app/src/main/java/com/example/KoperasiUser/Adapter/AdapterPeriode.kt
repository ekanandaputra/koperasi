package com.example.KoperasiUser.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.KoperasiUser.Menu.RiwayatActivity
import com.example.KoperasiUser.Periode
import com.example.KoperasiUser.R
import kotlinx.android.synthetic.main.layout_periode.view.*

class AdapterPeriode(private val context: Context, private val arrayList: ArrayList<Periode>) : RecyclerView.Adapter<AdapterPeriode.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.layout_periode,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.tvPeriode.text = arrayList!!.get(position)?.periode_awal + " / " + arrayList!!.get(position)?.periode_akhir

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id_periode", arrayList!!.get(position)?.id)
            bundle.putString("id_user", arrayList!!.get(position)?.id_user)
            bundle.putString("periode", arrayList!!.get(position)?.periode_awal + " / " + arrayList!!.get(position)?.periode_akhir)
            val intent = Intent(context, RiwayatActivity::class.java)
            intent.putExtras(bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}