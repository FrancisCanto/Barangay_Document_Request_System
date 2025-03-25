package com.example.barangaydocumentrequestsystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val usernameField: EditText = findViewById(R.id.fullname)
        val emailField: EditText = findViewById(R.id.email)
        val passwordField: EditText = findViewById(R.id.password)
        val confpasswordField: EditText = findViewById(R.id.confirmpassword)
        val addressField: EditText = findViewById(R.id.address)
        val contactNumberField: EditText = findViewById(R.id.contact)
        val signUpButton: Button = findViewById(R.id.register_btn)
        val loginText: TextView = findViewById(R.id.signin_btn)

        signUpButton.setOnClickListener {
            val usernameInput = usernameField.text.toString()
            val emailInput = emailField.text.toString()
            val passwordInput = passwordField.text.toString()
            val addressInput = addressField.text.toString()
            val contactNumberInput = contactNumberField.text.toString()
            val confPasswordInput = confpasswordField.text.toString()

            if (usernameInput.isEmpty() && emailInput.isEmpty() && passwordInput.isEmpty() &&
                addressInput.isEmpty() && contactNumberInput.isEmpty() && passwordInput.isEmpty() && confPasswordInput.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(emailInput)) {
                Toast.makeText(this, "Enter a valid email!", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(passwordInput)) {
                Toast.makeText(this, "Password must be at least 8 characters!", Toast.LENGTH_SHORT).show()
            }
            else if (passwordInput != confPasswordInput) {
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show()

            }
            else {

                val intent = Intent(this, AgreementActivity::class.java)
                startActivity(intent)
                registerUser(usernameInput, emailInput, passwordInput, addressInput, contactNumberInput)
            }
        }
        loginText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }

    private fun registerUser(username: String, email: String, password: String, address: String, contactNumber: String) {
        RetrofitClient.instance.registerUser(username, email, password, address, contactNumber)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignupActivity, "Registered Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@SignupActivity, "Registration Failed: $errorBody", Toast.LENGTH_LONG).show()
                    }
                }


                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

}
