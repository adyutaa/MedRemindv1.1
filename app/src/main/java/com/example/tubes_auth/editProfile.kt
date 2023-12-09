package com.example.tubes_auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class editProfile : AppCompatActivity() {

    private lateinit var autoCompleteTextViewCountry: AutoCompleteTextView
    private lateinit var textInputLayoutPhoneNumber: TextInputLayout
    private lateinit var editTextPhoneNumber: TextInputEditText
    private lateinit var editProfileImageView: ImageView
    private lateinit var editIcon: ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                editProfileImageView.setImageURI(imageUri)
            }
        }

    private var selectedPhotoUri: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        databaseReference =
            FirebaseDatabase.getInstance().reference.child("users").child(currentUser.uid)

        val saveButton: Button = findViewById(R.id.buttonSubmit)
        saveButton.setOnClickListener {
            val newFullName = findViewById<TextInputEditText>(R.id.etFullname).text.toString()
            val newEmail = findViewById<TextInputEditText>(R.id.etLabel).text.toString()

            if (newEmail.isNotEmpty()) {
                try {
                    // Perbarui email di Firebase Authentication
                    currentUser.updateEmail(newEmail).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Perbarui data pengguna di Firebase Realtime Database
                            databaseReference.child("email").setValue(newEmail)

                            // Intent untuk mengirim data kembali ke ProfileActivity
                            val intent = Intent(this@editProfile, Profile::class.java)
                            intent.putExtra("EMAIL", newEmail)
                            intent.putExtra("PHOTO_URI", selectedPhotoUri)

                            // Mulai aktivitas ProfileActivity dengan membawa data baru
                            startActivity(intent)
                        } else {
                            throw task.exception!!
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Failed to update email: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }


            editProfileImageView = findViewById(R.id.editProfileImageView)
            editIcon = findViewById(R.id.editIcon)

//            editIcon.setOnClickListener {
//                openGallery()
//            }

            autoCompleteTextViewCountry = findViewById(R.id.actvCountry)
            textInputLayoutPhoneNumber = findViewById(R.id.tiPhone)
            editTextPhoneNumber = findViewById(R.id.etPhone)

            // Inisialisasi Spinner dengan adapter negara
            val countries = arrayOf("Indonesia", "United States")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            autoCompleteTextViewCountry.setAdapter(adapter)

            // Tambahkan listener untuk menanggapi perubahan pilihan
            autoCompleteTextViewCountry.setOnItemClickListener { _, _, position, _ ->
                val etPhone: TextInputEditText = findViewById(R.id.etPhone)

                if (position == 0) { // Jika Indonesia dipilih
                    etPhone.setText("+62 ") // Kode negara Indonesia
                    // Tambahkan ikon atau gambar lambang negara Indonesia jika diperlukan
                } else if (position == 1) { // Jika Indonesia dipilih
                    etPhone.setText("+1 ") // Kode negara Indonesia
                    // Tambahkan ikon atau gambar lambang negara Indonesia jika diperlukan
                } else {
                    etPhone.setText("") // Kosongkan jika negara lain dipilih
                    // Hapus ikon atau gambar lambang negara Indonesia jika diperlukan
                }
            }
        }

//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        getContent.launch(intent)
//    }
    }
}
