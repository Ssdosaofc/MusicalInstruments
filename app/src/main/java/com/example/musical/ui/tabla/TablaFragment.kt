package com.example.musical.ui.tabla

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.databinding.FragmentTablaBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class TablaFragment : Fragment() {

    private var _binding: FragmentTablaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pianoViewModel =
            ViewModelProvider(this).get(TablaViewModel::class.java)

        _binding = FragmentTablaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val youTubePlayerView = binding.tab1
        lifecycle.addObserver(youTubePlayerView)

        val right = binding.right
        val left = binding.left
        val lesson = binding.lesson
        val desc = binding.desc

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                var videoId = "S0Q4gqBUs7c"
                youTubePlayer.loadVideo(videoId, 0f)
                left.visibility = View.INVISIBLE

                right.setOnClickListener {
                    left.visibility = View.VISIBLE
                    when (videoId) {
                        "S0Q4gqBUs7c" -> {
                            videoId = "GmcfVozXPoc"
                            lesson.text = "Lesson 2"
                            desc.text = "Basic Tabla Bols Playing Techniques"
                        }
                        "GmcfVozXPoc" -> {
                            lesson.text = "Lesson 3"
                            videoId = "AeRjwvT9Id4"
                            desc.text = "Concept of Khuli, Mudi and Kayeda"
                        }
                        "AeRjwvT9Id4" -> {
                            videoId = "GTow0z9Rp7E"
                            lesson.text = "Lesson 4"
                            desc.text = "Practice of Dha Terekete Tak"
                        }
                        "GTow0z9Rp7E" -> {
                            videoId = "vW918hOHD9Q"
                            lesson.text = "Lesson 5"
                            desc.text = "Practice based on Tere Kete Tak, Kat GheGhe Tete Kat"
                            right.visibility = View.INVISIBLE
                        }
                    }
                }

                left.setOnClickListener {
                    when (videoId) {
                        "GmcfVozXPoc" -> {
                            videoId = "S0Q4gqBUs7c"
                            lesson.text = "Lesson 1"
                            desc.text = "Basics of Tabla, Parts of Tabla, Important Tabla Bol"
                            left.visibility = View.INVISIBLE
                        }
                        "AeRjwvT9Id4" -> {
                            lesson.text = "Lesson 2"
                            videoId = "GmcfVozXPoc"
                            desc.text = "Basic Tabla Bols Playing Techniques"
                        }
                        "GTow0z9Rp7E" -> {
                            videoId = "AeRjwvT9Id4"
                            lesson.text = "Lesson 3"
                            desc.text = "Concept of Khuli, Mudi and Kayeda"
                        }
                        "vW918hOHD9Q" -> {
                            videoId = "GTow0z9Rp7E"
                            lesson.text = "Lesson 4"
                            desc.text = "Practice of Dha Terekete Takt"
                        }
                    }
                }
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}