package com.rosyid.loginfirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.rosyid.loginfirebase.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register()
        login()

        auth = FirebaseAuth.getInstance()
    }

    fun register() {
        val btnRegister: Button = findViewById(R.id.btnSignUp)

        btnRegister.setOnClickListener {

//            AUTH
            val email : EditText = findViewById(R.id.etEmail)
            val password : EditText = findViewById(R.id.etPassword)

            val etEmail = email.text.toString().trim()
            val etPassword = password.text.toString().trim()
            if (etEmail.isEmpty()) {
                email.error = "Email Harus Diisi!"
                email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail).matches()) {
                email.error = "Email Tidak Valid!"
                email.requestFocus()
                return@setOnClickListener
            }

            if (password.length() < 6) {
                password.error = "Password Tidak Boleh Kurang dari 6"
                password.requestFocus()
                return@setOnClickListener
            }

            registerUser(etEmail, etPassword)

        }
    }

    private fun registerUser(etEmail: String, etPassword: String) {
        auth.createUserWithEmailAndPassword(etEmail, etPassword)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Intent(this@RegisterActivity, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }

    fun login() {
        val btnLogin : Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            Intent(this@RegisterActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@RegisterActivity, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}