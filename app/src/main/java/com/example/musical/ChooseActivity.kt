package com.example.musical

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChooseActivity : AppCompatActivity() {
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

        guitar.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","guitar")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
        keyboard.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","keyboard")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
        piano.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","piano")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
        violin.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","violin")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
        tabla.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","tabla")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
        harmonium.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","harmonium")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
        vocals.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","vocals")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
        rec.setOnClickListener{
           val intent =Intent(this,MainActivity::class.java)
            intent.putExtra("fragment","recorder")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left_out)
            finish()
        }
    }
}