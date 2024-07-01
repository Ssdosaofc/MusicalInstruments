package com.example.musical.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musical.ChooseActivity
import com.example.musical.R
import com.example.musical.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.getstarted)

        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN,R.anim.slide_left,R.anim.slide_left_out)
//            overridePendingTransition(R.anim.slide_right, R.anim.slide_left_out)
            finish()
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