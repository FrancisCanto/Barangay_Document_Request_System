package com.example.barangaydocumentrequestsystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val logout_btn: Button = findViewById(R.id.logout_btn)
        val back_btn: ImageButton = findViewById(R.id.back_btn)
        val changepass_btn: ImageButton = findViewById(R.id.changepass)

        // Retrieve stored user ID
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userId = sharedPref.getString("user_id", null) // ✅ Store as plain string

        Log.d("ProfileActivity", "Retrieved user_id: $userId") // Debugging log

        if (!userId.isNullOrEmpty()) {
            fetchUserProfile(userId) // Fetch and display user profile
        } else {
            Log.e("ProfileActivity", "User ID is NULL!")
            Toast.makeText(this, "Error: User ID not found!", Toast.LENGTH_SHORT).show()
        }

        logout_btn.setOnClickListener {
            sharedPref.edit().clear().apply() // Clear saved user data
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        changepass_btn.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        back_btn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUserProfile(userId: String) {
        RetrofitClient.instance.getUserProfile(userId)
            .enqueue(object : Callback<UserApiService.User> {
                override fun onResponse(call: Call<UserApiService.User>, response: Response<UserApiService.User>) {
                    Log.d("ProfileActivity", "API Response received")

                    // Print the raw response for debugging
                    Log.d("ProfileActivity", "Raw Response: ${response.body()}")

                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            Log.d("ProfileActivity", "Parsed User Data: $user")

                            findViewById<TextView>(R.id.name).text = user.username ?: "N/A"
                            findViewById<TextView>(R.id.UserEmail).text = user.email ?: "N/A"
                            findViewById<TextView>(R.id.username2).text = user.username ?: "N/A"
                            findViewById<TextView>(R.id.conNum).text = user.contact_num ?: "N/A"
                        } else {
                            Log.e("ProfileActivity", "User data is NULL")
                            Toast.makeText(this@ProfileActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorResponse = response.errorBody()?.string()
                        Log.e("ProfileActivity", "Failed to load profile: $errorResponse")
                        Toast.makeText(this@ProfileActivity, "Error: $errorResponse", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserApiService.User>, t: Throwable) {
                    Log.e("ProfileActivity", "API Error: ${t.message}")
                    Toast.makeText(this@ProfileActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }




}
