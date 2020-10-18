package com.example.KoperasiUser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.KoperasiUser.Notifikasi
import com.example.KoperasiUser.R
import kotlinx.android.synthetic.main.layout_notif.view.*

class AdapterNotif(private val context: Context, private val arrayList: ArrayList<Notifikasi>) : RecyclerView.Adapter<AdapterNotif.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.layout_notif,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.tvJudul.text = arrayList!!.get(position)?.judul
        holder.view.tvDeskripsi.text = arrayList?.get(position)?.deskripsi
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