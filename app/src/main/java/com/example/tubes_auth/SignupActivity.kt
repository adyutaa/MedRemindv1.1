package com.example.tubes_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tubes_auth.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()
            val confirmPass = binding.etConfirmPass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intentLogin = Intent(this, LoginActivity::class.java)
                            startActivity(intentLogin)

                        }else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    Toast.makeText(this,"Password doesn't match!", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this,"Fields can not be empty!", Toast.LENGTH_SHORT).show()
            }
        }



        val NavigateBackWelcome = findViewById<Button>(R.id.btnBackWelcome)
        NavigateBackWelcome.setOnClickListener {
            onBackPressed()
        }
    }
}