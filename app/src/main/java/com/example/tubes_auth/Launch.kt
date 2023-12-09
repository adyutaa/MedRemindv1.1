package com.example.tubes_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Launch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)


        Handler().postDelayed({
            val intent = Intent(this, WelcomeLogin::class.java)
            startActivity(intent)
            finish()
        }, 3000)


    }
}