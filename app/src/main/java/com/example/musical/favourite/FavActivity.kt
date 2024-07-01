package com.example.musical.favourite

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musical.R

class FavActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var favViewModel: FavViewModel
    private lateinit var adapter: FavAdapter
//    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    val ref = FirebaseDatabase.getInstance().getReference("Users")
    val list = mutableListOf<Video>()

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            this.onBackPressed()
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE,R.anim.slide_right,R.anim.slide_right_out)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        favViewModel = ViewModelProvider(this)[FavViewModel::class.java]

        adapter = FavAdapter(this, list)
        recyclerView.adapter = adapter

        favViewModel.fetchFav()

        favViewModel.videos.observe(this) {
            videos:List<Video> ->
//            fetchFavoriteMovies()
            adapter.updateData(videos)
        }
    }

//    private fun fetchFavoriteMovies() {
//        val favoritesRef = ref.child(firebaseAuth.uid.toString()).child("Favourites")
//
//        favoritesRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                list.clear()
//                for (dataSnapshot: DataSnapshot in snapshot.children) {
//                    val video = dataSnapshot.getValue(Video::class.java)
//                    video?.let {
//                        list.add(it)
//                    }
//                }
//                adapter.notifyDataSetChanged()
//                Log.d("FavActivity", "Fetched ${list.size} videos")
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("FavActivity", "Database error: ${error.message}")
//            }
//        })
//    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }
}