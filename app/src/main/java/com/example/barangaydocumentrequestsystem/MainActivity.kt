package com.example.barangaydocumentrequestsystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.login_btn)
        val forgotPasswordTextView: TextView = findViewById(R.id.forgotpass_btn)
        val signUpTextView: TextView = findViewById(R.id.signup_tv)

        forgotPasswordTextView.setOnClickListener {
            startActivity(Intent(this, ForgotpassActivity::class.java))
        }

        signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Enter a valid email!", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must be at least 8 characters!", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        RetrofitClient.instance.loginUser(email, password)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        response.body()?.let { responseBody ->
                            val responseText = responseBody.string().trim()
                            Log.d("MainActivity", "Login Response: $responseText")

                            try {
                                val jsonResponse = JSONObject(responseText)
                                if (jsonResponse.has("error")) {
                                    Toast.makeText(this@MainActivity, "Login Failed: ${jsonResponse.getString("error")}", Toast.LENGTH_SHORT).show()
                                } else if (jsonResponse.has("user_id")) {
                                    val userId = jsonResponse.getString("user_id") // Extract user_id

                                    val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                                    sharedPref.edit().putString("user_id", userId).apply()

                                    Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this@MainActivity, "Unexpected server response", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Log.e("MainActivity", "JSON Parsing Error: ${e.message}")
                                Toast.makeText(this@MainActivity, "Response Parsing Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("MainActivity", "Network Error: ${t.message}")
                    Toast.makeText(this@MainActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
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
