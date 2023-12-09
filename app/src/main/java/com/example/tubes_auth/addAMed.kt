package com.example.tubes_auth

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale


class addAMed : AppCompatActivity() {


    private var etJam: EditText? = null
    private var etKalender: EditText? = null
    private var etObat: EditText? = null
    private var btnJam: ImageButton? = null
    private var btnKalender: ImageButton? = null
    private var btnDone: Button? = null
    private var jam = 0
    private var menit = 0
    private var jam2 = 0
    private var menit2 = 0
    private var tahun = 0
    private var bulan = 0
    private var tanggal = 0
    private var tahun2 = 0
    private var bulan2 = 0
    private var tanggal2 = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_amed)

        etJam = findViewById(R.id.etJam)
        etKalender = findViewById(R.id.etTanggal)
        etObat = findViewById(R.id.etObat)
        btnJam = findViewById(R.id.btnJam)
        btnKalender = findViewById(R.id.btnTanggal)
        btnDone = findViewById(R.id.btnDone)

        val isEditMode = intent.getBooleanExtra("editMode", false)
        if (isEditMode) {
            val jamInput = intent.getStringExtra("jamInput") ?: ""
            val kalenderInput = intent.getStringExtra("kalenderInput") ?: ""
            val obatInput = intent.getStringExtra("obatInput") ?: ""

            etJam?.setText(jamInput)
            etKalender?.setText(kalenderInput)
            etObat?.setText(obatInput)
        }

        btnJam?.setOnClickListener {
            val calendar = Calendar.getInstance()
            jam = calendar[Calendar.HOUR_OF_DAY]
            menit = calendar[Calendar.MINUTE]


            val dialog = TimePickerDialog(
                this@addAMed,
                { _, hourOfDay, minute ->
                    jam = hourOfDay
                    menit = minute
                    etJam?.setText(
                        String.format(
                            Locale.getDefault(),
                            "%d:%d %s",
                            if (jam <= 12) jam else jam - 12,
                            menit,
                            if (jam < 12) "am" else "pm"
                        )
                    )
                }, jam, menit, true
            )


            dialog.show()
        }


        etJam?.setOnClickListener {
            val calendar = Calendar.getInstance()
            jam2 = calendar[Calendar.HOUR_OF_DAY]
            menit2 = calendar[Calendar.MINUTE]


            val dialog = TimePickerDialog(
                this@addAMed,
                { _, hourOfDay, minute ->
                    jam2 = hourOfDay
                    menit2 = minute
                    etJam?.setText(
                        String.format(
                            Locale.getDefault(),
                            "%d:%d %s",
                            if (jam2 <= 12) jam2 else jam2 - 12,
                            menit2,
                            if (jam2 < 12) "am" else "pm"
                        )
                    )
                }, jam2, menit2, true
            )


            dialog.show()
        }


        btnKalender?.setOnClickListener {
            val calendar = Calendar.getInstance()
            tahun = calendar[Calendar.YEAR]
            bulan = calendar[Calendar.MONTH]
            tanggal = calendar[Calendar.DAY_OF_MONTH]


            val dialog = DatePickerDialog(
                this@addAMed,
                { _, year, month, dayOfMonth ->
                    tahun = year
                    bulan = month
                    tanggal = dayOfMonth
                    etKalender?.setText("$tanggal - ${bulan + 1} - $tahun")
                }, tahun, bulan, tanggal
            )


            dialog.show()
        }


        etKalender?.setOnClickListener {
            val calendar = Calendar.getInstance()
            tahun2 = calendar[Calendar.YEAR]
            bulan2 = calendar[Calendar.MONTH]
            tanggal2 = calendar[Calendar.DAY_OF_MONTH]


            val dialog = DatePickerDialog(
                this@addAMed,
                { _, year, month, dayOfMonth ->
                    tahun2 = year
                    bulan2 = month
                    tanggal2 = dayOfMonth
                    etKalender?.setText("$tanggal2 - ${bulan2 + 1} - $tahun2")
                }, tahun2, bulan2, tanggal2
            )


            dialog.show()
        }

        btnDone?.setOnClickListener {
            val jamInput = etJam?.text.toString()
            val kalenderInput = etKalender?.text.toString()
            val obatInput = etObat?.text.toString()
            val isEditMode = intent.getBooleanExtra("editMode", false)
            val position = intent.getIntExtra("position", -1)

            val resultIntent = Intent()
            resultIntent.putExtra("jamInput", jamInput)
            resultIntent.putExtra("kalenderInput", kalenderInput)
            resultIntent.putExtra("obatInput", obatInput)
            resultIntent.putExtra("editMode", isEditMode)
            resultIntent.putExtra("position", position)

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}