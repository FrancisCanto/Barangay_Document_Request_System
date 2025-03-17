package com.example.barangaydocumentrequestsystem

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DocumentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_documents)

        val backButton: ImageButton = findViewById(R.id.back_btn)
        val clearanceButton: ImageButton = findViewById(R.id.clearance)
        val residencyButton: ImageButton = findViewById(R.id.residency)
        val indigencyButton: ImageButton = findViewById(R.id.indigency)
        val businessClearanceButton: ImageButton = findViewById(R.id.business_clearance)
        val goodMoralButton: ImageButton = findViewById(R.id.good_moral)
        val noPendingCaseButton: ImageButton = findViewById(R.id.no_pending_case)
        val soloParentButton: ImageButton = findViewById(R.id.solo_parent)
        val calamityAssistanceButton: ImageButton = findViewById(R.id.calamity_assistance)
        val tenancyButton: ImageButton = findViewById(R.id.tenancy)

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Clearance Button
        clearanceButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Residency Button
        residencyButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Indigency Button
        indigencyButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Business Clearance Button
        businessClearanceButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Good Moral Button
        goodMoralButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // No Pending Case Button
        noPendingCaseButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Solo Parent Button
        soloParentButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Calamity Assistance Button
        calamityAssistanceButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Tenancy Button
        tenancyButton.setOnClickListener {
            val intent = Intent(this, RequestFormActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}