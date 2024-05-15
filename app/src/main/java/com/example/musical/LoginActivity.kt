package com.example.musical

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailbox= findViewById<TextInputEditText>(R.id.email)
        val passwordbox = findViewById<TextInputEditText>(R.id.password)
        val newuser = findViewById<TextView>(R.id.newh)
        val login = findViewById<Button>(R.id.loginbutton)

        newuser.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener{
            val email = emailbox.text.toString()
            val password = passwordbox.text.toString()
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, ChooseActivity::class.java)
                        startActivity(intent)
                        finish()

                    }else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, ChooseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}