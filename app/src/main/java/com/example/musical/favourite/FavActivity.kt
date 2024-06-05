package com.example.musical.favourite

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musical.R
import com.example.musical.viewPager.Video
import com.google.firebase.auth.FirebaseAuth

class FavActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var favViewModel:FavViewModel
    private lateinit var adapter:FavAdapter
    var list = arrayListOf<Video>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
        adapter = FavAdapter(this,list)
        recyclerView.adapter = adapter

        favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid

        userId?.let { favViewModel.fetchFav(list) }

        favViewModel.videos.observe(this) { videos: List<Video> ->
            videos.forEach { video ->
                Log.d("FavActivity", "Collection: ${video.collection}, Lesson: ${video.lesson}, Desc: ${video.desc}")
            }
            adapter.notifyDataSetChanged()
        }
    }
}