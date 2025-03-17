package com.example.barangaydocumentrequestsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/*class Registration2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration2)

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val confirmPassword: EditText = findViewById(R.id.confirmpassword)
        val registerButton: Button = findViewById(R.id.signin_btn)

        registerButton.setOnClickListener {
            var isValid = true

            if (username.text.toString().isEmpty()) {
                username.error = "Username is required"
                isValid = false
            }

            if (password.text.toString().length < 8) {
                password.error = "Password must be at least 8 characters"
                isValid = false
            }
            if (password.text.toString() != confirmPassword.text.toString()) {
                confirmPassword.error = "Passwords do not match"
                isValid = false
            }

            if (isValid) {
                val intent = Intent(this, AgreementActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
*/