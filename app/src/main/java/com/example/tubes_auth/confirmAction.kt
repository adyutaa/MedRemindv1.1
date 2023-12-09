package com.example.tubes_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class confirmAction : AppCompatActivity() {
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_action)

        position = intent.getIntExtra("position", -1)

        val buttonYes: Button = findViewById(R.id.buttonY)
        val buttonNo: Button = findViewById(R.id.buttonX)

        buttonYes.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("isDeleteConfirmed", true)
            resultIntent.putExtra("position", position)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        buttonNo.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("isDeleteConfirmed", false)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}