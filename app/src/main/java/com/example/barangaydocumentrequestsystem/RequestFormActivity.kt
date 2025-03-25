package com.example.barangaydocumentrequestsystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestFormActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_form)

        val fullname: EditText = findViewById(R.id.fullname)
        val address: EditText = findViewById(R.id.address)
        val contact: EditText = findViewById(R.id.contact)
        val purpose: EditText = findViewById(R.id.purpose)
        val document: EditText = findViewById(R.id.document)
        val submitButton: Button = findViewById(R.id.submit_btn)
        val backButton: ImageButton = findViewById(R.id.back_btn)

        // Get the array of documents for the dropdown
        val documents = resources.getStringArray(R.array.documents)

// Create an ArrayAdapter to bind the data to the AutoCompleteTextView
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, documents)

// Find the AutoCompleteTextView and set the adapter
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.document)
        autoCompleteTextView.setAdapter(arrayAdapter)


        // Retrieve stored user ID
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userId = sharedPref.getString("user_id", null) // ✅ Store as plain string

        Log.d("RequestFormActivity", "Retrieved user_id: $userId") // Debugging log

        if (!userId.isNullOrEmpty()) {
            fetchUserProfile(userId) // Fetch and display user profile
        } else {
            Log.e("RequestFormActivity", "User ID is NULL!")
            Toast.makeText(this, "Error: User ID not found!", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        submitButton.setOnClickListener {

            val fullnameInput = fullname.text.toString()
            val addressInput = address.text.toString()
            val purposeInput = purpose.text.toString()
            val contactInput = contact.text.toString()
            val documentInput = document.text.toString()

            if (fullnameInput.isNotEmpty() && addressInput.isNotEmpty() && purposeInput.isNotEmpty() &&
                contactInput.isNotEmpty() && documentInput.isNotEmpty()) {

                val intent = Intent(this, DocumentsActivity::class.java)
                startActivity(intent)
                userRequest(fullnameInput, addressInput, purposeInput, contactInput, documentInput)
                Toast.makeText(this, "Request submitted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun userRequest(username: String, address: String, purpose: String, contactNum: String, docu: String) {
        RetrofitClient.instance.userRequest(username, address, purpose, contactNum, docu)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RequestFormActivity, "Requested Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@RequestFormActivity, "Request Failed!: $errorBody", Toast.LENGTH_LONG).show()
                    }
                }


                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@RequestFormActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun fetchUserProfile(userId: String) {
        RetrofitClient.instance.getUserProfile(userId)
            .enqueue(object : Callback<UserApiService.User> {
                override fun onResponse(call: Call<UserApiService.User>, response: Response<UserApiService.User>) {
                    Log.d("RequestFormActivity", "API Response received")

                    // Print the raw response for debugging
                    Log.d("RequestFormActivity", "Raw Response: ${response.body()}")

                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            Log.d("RequestFormActivity", "Parsed User Data: $user")

                            findViewById<EditText>(R.id.fullname).text = Editable.Factory.getInstance().newEditable(user.username ?: "N/A")
                            findViewById<EditText>(R.id.address).text = Editable.Factory.getInstance().newEditable(user.email ?: "N/A")
                            findViewById<EditText>(R.id.contact).text = Editable.Factory.getInstance().newEditable(user.contact_num ?: "N/A")
                        } else {
                            Log.e("RequestFormActivity", "User data is NULL")
                            Toast.makeText(this@RequestFormActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorResponse = response.errorBody()?.string()
                        Log.e("RequestFormActivity", "Failed to load profile: $errorResponse")
                        Toast.makeText(this@RequestFormActivity, "Error: $errorResponse", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserApiService.User>, t: Throwable) {
                    Log.e("RequestFormActivity", "API Error: ${t.message}")
                    Toast.makeText(this@RequestFormActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

}
