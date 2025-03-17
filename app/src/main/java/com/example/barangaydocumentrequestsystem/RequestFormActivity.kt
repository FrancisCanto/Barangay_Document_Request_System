package com.example.barangaydocumentrequestsystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        backButton.setOnClickListener {
            val intent = Intent(this, DocumentsActivity::class.java)
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
}
