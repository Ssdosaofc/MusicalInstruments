package com.example.musical.favourite

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musical.R
import com.example.musical.viewPager.openFullScreen
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class ExpandActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_expand)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.tab1)
        val videoId = intent.getStringExtra("videoId")

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (videoId != null) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        youTubePlayer.loadVideo(videoId, 0f)
                    },500)
                }
                youTubePlayer.pause()
            }
        })

        val zoom = findViewById<LinearLayout>(R.id.zoom)
        if (videoId != null) {
            openFullScreen(zoom, this, videoId)
        }
    }
}