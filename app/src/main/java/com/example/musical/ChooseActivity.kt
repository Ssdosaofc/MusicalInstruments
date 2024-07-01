package com.example.musical

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChooseActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choose)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val guitar = findViewById<CardView>(R.id.cardView)
        val keyboard = findViewById<CardView>(R.id.cardView2)
        val piano = findViewById<CardView>(R.id.cardView3)
        val violin = findViewById<CardView>(R.id.cardView4)
        val tabla = findViewById<CardView>(R.id.cardView5)
        val harmonium = findViewById<CardView>(R.id.cardView6)
        val vocals = findViewById<CardView>(R.id.cardView7)
        val rec = findViewById<TextView>(R.id.record)

        setClickListener(guitar, "guitar")
        setClickListener(keyboard, "keyboard")
        setClickListener(piano, "piano")
        setClickListener(violin, "violin")
        setClickListener(tabla, "tabla")
        setClickListener(harmonium, "harmonium")
        setClickListener(vocals, "vocals")
        setClickListener(rec, "recorder")
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun setClickListener(view: View, fragmentName: String) {
        view.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fragment", fragmentName)
            startActivity(intent)
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN,R.anim.slide_left,R.anim.slide_left_out)
            // overridePendingTransition(R.anim.slide_right, R.anim.slide_left_out)
            finish()
        }
    }
}