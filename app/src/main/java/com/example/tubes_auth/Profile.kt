package com.example.tubes_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Profile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()


        val editProfileButton: Button = findViewById(R.id.editProfileButton)
        editProfileButton.setOnClickListener {
            // Intent untuk membuka halaman EditProfileActivity
            val intent = Intent(this@Profile, editProfile::class.java)
            startActivity(intent)
        }

        val btnLogout: Button = findViewById(R.id.buttonLogout)
        btnLogout.setOnClickListener {
            // Fungsi untuk logout
            auth.signOut()

            // Intent untuk membuka halaman LoginActivity
            val intent = Intent(this@Profile, LoginActivity::class.java)
            startActivity(intent)

            // Menutup aktivitas Profile (agar tidak dapat kembali ke halaman Profile setelah logout)
            finish()
        }


        val fullNameTextView: TextView = findViewById(R.id.nameTextView)
        val emailTextView: TextView = findViewById(R.id.emailTextView)
        val photoImageView: ImageView = findViewById(R.id.profileImageView)

        // Menerima data dari Intent yang dikirim oleh EditProfileActivity
        val intent = intent
        val newFullName = intent.getStringExtra("FULL_NAME")
        val newEmail = intent.getStringExtra("EMAIL")
        val newPhotoUri = intent.getStringExtra("PHOTO_URI")

        // Menetapkan data baru ke tampilan
        fullNameTextView.text = newFullName
        emailTextView.text = newEmail

        // Load dan terapkan foto menggunakan library atau metode yang sesuai
        // ...

        val editProfilebutton: Button = findViewById(R.id.editProfileButton)
        editProfileButton.setOnClickListener {
            // Intent untuk membuka halaman EditProfileActivity
            val editIntent = Intent(this@Profile, editProfile::class.java)
            startActivity(editIntent)


        }
    }
}