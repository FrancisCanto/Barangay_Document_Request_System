package com.example.barangaydocumentrequestsystem

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AgreementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agreement)

        val continueButton: TextView = findViewById(R.id.continue_btn)
        val agreeCheckBox: CheckBox = findViewById(R.id.checkbox)

        continueButton.setOnClickListener {
            if (agreeCheckBox.isChecked) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Account Registered", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "You must agree to the terms to continue", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}