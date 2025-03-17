/*package com.example.barangaydocumentrequestsystem

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val fullname: EditText = findViewById(R.id.fullname)
        val address: EditText = findViewById(R.id.address)
        val contact: EditText = findViewById(R.id.contact)
        val email: EditText = findViewById(R.id.email)
        val nextButton: Button = findViewById(R.id.next_btn)
        val signInButton: TextView = findViewById(R.id.signin_btn)

        nextButton.setOnClickListener {
            var isValid = true

            if (fullname.text.isEmpty()) {
                fullname.error = "Full name is required"
                isValid = false
            }

            if (address.text.isEmpty()) {
                address.error = "Address is required"
                isValid = false
            }

            val contactText = contact.text.toString()
            if (contactText.isEmpty()) {
                contact.error = "Contact number is required"
                isValid = false
            } else if (contactText.length != 11 || !contactText.all { it.isDigit() }) {
                contact.error = "Contact number must be 11 digits"
                isValid = false
            }

            if (email.text.isEmpty()) {
                email.error = "Email is required"
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                email.error = "Invalid email address"
                isValid = false
            }

            if (isValid) {
                val intent = Intent(this, Registration2Activity::class.java)
                startActivity(intent)
            }
        }

        signInButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
*/