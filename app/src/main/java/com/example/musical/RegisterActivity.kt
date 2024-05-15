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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    data class userDetails(val email: String, val password:String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val emailbox= findViewById<TextInputEditText>(R.id.register)
        val passwordbox = findViewById<TextInputEditText>(R.id.password)
        val verifybox = findViewById<TextInputEditText>(R.id.verify)
        val already = findViewById<TextView>(R.id.already)
        val login = findViewById<Button>(R.id.registerbutton)

        already.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener{
            val email = emailbox.text.toString()
            val password = passwordbox.text.toString()
            val verifypassword = verifybox.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()

            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(verifypassword)) {
                Toast.makeText(this, "Verify Password", Toast.LENGTH_SHORT).show()

            } else if (verifypassword != password) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val ref: DatabaseReference =
                            FirebaseDatabase.getInstance().getReference("Users")

                        val userDetails = userDetails(email, password)

                        ref.child("users")
                            .child(auth.uid.toString()).child("User Details")
                            .setValue(userDetails)

                        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, ChooseActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        Toast.makeText(
                            this,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, ChooseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}