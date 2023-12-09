package com.example.tubes_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.tubes_auth.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider




class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            val email = binding.enterEmail.text.toString()
            val pass = binding.enterPass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intentDashboard = Intent(this, MainActivity::class.java)
                        startActivity(intentDashboard)
                    }
                }

            }else {
                Toast.makeText(this,"Field can not be empty!", Toast.LENGTH_SHORT).show()
            }
        }


        val currentUser = firebaseAuth.currentUser

        if(currentUser != null) {
            // User already logged in, navigate to dashboardActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        val signInButton = findViewById<Button>(R.id.btnGoogle)
        signInButton.setOnClickListener{
            signIn()
        }

        val NavigateBackIntent = findViewById<Button>(R.id.btnBackWelcome)
        NavigateBackIntent.setOnClickListener {
            onBackPressed()
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google SIgn In failed ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed is as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Launch::class.java))
                    finish()
                }else {
                    Toast.makeText(this,"Authentication failed", Toast.LENGTH_SHORT).show()
                }

            }
    }





}