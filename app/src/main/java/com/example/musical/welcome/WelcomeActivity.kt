package com.example.musical.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.musical.ChooseActivity
import com.example.musical.R
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val button: Button = findViewById(R.id.button2)

        button.setOnClickListener {
            val intent = Intent(this, WelcomeActivity2::class.java)
            startActivity(intent)
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN,R.anim.slide_left,R.anim.slide_left_out)
//            overridePendingTransition(R.anim.slide_right, R.anim.slide_left_out)
        }
    }

    override fun onStart() {
        super.onStart()
        auth= FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, ChooseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}