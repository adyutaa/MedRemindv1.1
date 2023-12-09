package com.example.tubes_auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


const val ADD_MED_REQUEST_CODE = 123
const val CONFIRM_ACTION_REQUEST_CODE = 456

class ObatAdapter(private val obatList: ArrayList<Obat>) : RecyclerView.Adapter<ObatAdapter.ObatViewHolder>() {

    class ObatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvObat: TextView = itemView.findViewById(R.id.tvObat)
        val tvJam: TextView = itemView.findViewById(R.id.tvJam)
        val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        val ivSampah: ImageView = itemView.findViewById(R.id.ivSampah)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_obat, parent, false)
        return ObatViewHolder(view)
    }


    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val currentItem = obatList[position]
        holder.tvTanggal.text = currentItem.tanggal
        holder.tvObat.text = currentItem.obat
        holder.tvJam.text = currentItem.jam

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, confirmAction::class.java)
            intent.putExtra("position", position)
            (holder.itemView.context as AppCompatActivity).startActivityForResult(intent, CONFIRM_ACTION_REQUEST_CODE)
        }

        holder.ivEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, addAMed::class.java)
            intent.putExtra("editMode", true)
            intent.putExtra("jamInput", currentItem.jam)
            intent.putExtra("kalenderInput", currentItem.tanggal)
            intent.putExtra("obatInput", currentItem.obat)
            intent.putExtra("position", position)

            (holder.itemView.context as AppCompatActivity).startActivityForResult(intent, ADD_MED_REQUEST_CODE)
        }


        holder.ivSampah.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setTitle("Konfirmasi")
            alertDialogBuilder.setMessage("Apakah Anda yakin ingin menghapus?")
            alertDialogBuilder.setPositiveButton("Ya") { _, _ ->
                obatList.removeAt(position)
                notifyDataSetChanged()
            }
            alertDialogBuilder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


    }


    override fun getItemCount() = obatList.size
}
