package com.example.barangaydocumentrequestsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp)

        val otpDigit1: EditText = findViewById(R.id.otp_digit_1)
        val verifyButton: Button = findViewById(R.id.verify_btn)
        val backButton: ImageButton = findViewById(R.id.back_btn)

        backButton.setOnClickListener {
            val intent = Intent(this, ForgotpassActivity::class.java)
            startActivity(intent)
            finish()
        }

        verifyButton.setOnClickListener {
            val otp1 = otpDigit1.text.toString().trim()

            if (otp1.isEmpty()) {
                Toast.makeText(this, "Please enter all OTP digits", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, NewpasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
