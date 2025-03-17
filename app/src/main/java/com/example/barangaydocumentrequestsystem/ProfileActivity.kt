package com.example.barangaydocumentrequestsystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        val logout_btn: Button = findViewById(R.id.logout_btn)
        val back_btn: ImageButton = findViewById(R.id.back_btn)
        val changepass_btn: ImageButton = findViewById(R.id.changepass)
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userId = sharedPref.getString("user_id", null)


        if (userId != null) {
            RetrofitClient.instance.getUserProfile(userId)
                .enqueue(object : Callback<UserApiService.User> {
                    override fun onResponse(call: Call<UserApiService.User>, response: Response<UserApiService.User>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            findViewById<TextView>(R.id.name).text = user?.username
                            findViewById<TextView>(R.id.UserEmail).text = user?.email
                            findViewById<TextView>(R.id.username2).text = user?.username
                            findViewById<TextView>(R.id.conNum).text = user?.contact
                        } else {
                            Toast.makeText(this@ProfileActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserApiService.User>, t: Throwable) {
                        Toast.makeText(this@ProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }


        logout_btn.setOnClickListener {
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}