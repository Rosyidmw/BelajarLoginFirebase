package com.rosyid.loginfirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.rosyid.loginfirebase.R

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout()
        auth = FirebaseAuth.getInstance()
    }

    fun logout() {
        val btnLogout : Button = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            auth.signOut()
            Intent(this@MainActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}