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
        val otpDigit2: EditText = findViewById(R.id.otp_digit_2)
        val otpDigit3: EditText = findViewById(R.id.otp_digit_3)
        val otpDigit4: EditText = findViewById(R.id.otp_digit_4)
        val verifyButton: Button = findViewById(R.id.verify_btn)
        val backButton: ImageButton = findViewById(R.id.back_btn)

        backButton.setOnClickListener {
            val intent = Intent(this, ForgotpassActivity::class.java)
            startActivity(intent)
            finish()
        }

        verifyButton.setOnClickListener {
            val otp1 = otpDigit1.text.toString().trim()
            val otp2 = otpDigit2.text.toString().trim()
            val otp3 = otpDigit3.text.toString().trim()
            val otp4 = otpDigit4.text.toString().trim()

            if (otp1.isEmpty() || otp2.isEmpty() || otp3.isEmpty() || otp4.isEmpty()) {
                Toast.makeText(this, "Please enter all OTP digits", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, NewpasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
