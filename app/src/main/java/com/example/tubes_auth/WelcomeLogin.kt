package com.example.tubes_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeLogin : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome_login)

        val loginIntent = findViewById<Button>(R.id.btnToLogin)
        loginIntent.setOnClickListener{
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)


        }

        val registrationIntent = findViewById<Button>(R.id.btnToRegistration)
        registrationIntent.setOnClickListener {
            val intentToRegistration = Intent(this, SignupActivity::class.java)
            startActivity(intentToRegistration)
        }

        }


    }



