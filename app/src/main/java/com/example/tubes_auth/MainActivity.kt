package com.example.tubes_auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var obatList: ArrayList<Obat>
    private lateinit var adapter: ObatAdapter


    private val ADD_MED_REQUEST_CODE = 123
    private val CONFIRM_ACTION_REQUEST_CODE = 456


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        obatList = ArrayList()
        adapter = ObatAdapter(obatList)
        recyclerView.adapter = adapter

        val ivProfile: ImageView = findViewById(R.id.ivProfile)
        ivProfile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val buttonTambah: Button = findViewById(R.id.buttonTambah)
        buttonTambah.setOnClickListener {
            val intent = Intent(this, addAMed::class.java)
            startActivityForResult(intent, ADD_MED_REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_MED_REQUEST_CODE && resultCode == RESULT_OK) {
            val isEditMode = data?.getBooleanExtra("editMode", false) ?: false

            if (isEditMode) {
                // Bagian ini akan dipanggil saat mengedit
                val tanggalEdited = data?.getStringExtra("kalenderInput") ?: ""
                val obatEdited = data?.getStringExtra("obatInput") ?: ""
                val jamEdited = data?.getStringExtra("jamInput") ?: ""

                val editedPosition = data?.getIntExtra("position", -1) ?: -1
                if (editedPosition != -1) {
                    obatList[editedPosition] = Obat(tanggalEdited, obatEdited, jamEdited)
                    adapter.notifyItemChanged(editedPosition)
                }
            } else {
                // Bagian ini akan dipanggil saat menambah obat baru
                val tanggal = data?.getStringExtra("kalenderInput") ?: ""
                val obat = data?.getStringExtra("obatInput") ?: ""
                val jam = data?.getStringExtra("jamInput") ?: ""

                val newObat = Obat(tanggal, obat, jam)
                obatList.add(newObat)
                adapter.notifyItemInserted(obatList.size - 1) // Menambahkan item baru pada posisi terakhir
            }
        }else if (requestCode == CONFIRM_ACTION_REQUEST_CODE && resultCode == RESULT_OK) {
            val isDeleteConfirmed = data?.getBooleanExtra("isDeleteConfirmed", false) ?: false
            val position = data?.getIntExtra("position", -1) ?: -1

            if (isDeleteConfirmed && position != -1) {
                obatList.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "Kerja Bagus!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}