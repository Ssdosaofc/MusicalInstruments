package com.example.musical.ui.vocals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.databinding.FragmentVocalsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VocalsFragment : Fragment() {

    private var _binding: FragmentVocalsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pianoViewModel =
            ViewModelProvider(this).get(VocalsViewModel::class.java)

        _binding = FragmentVocalsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val youTubePlayerView = binding.tab1
        lifecycle.addObserver(youTubePlayerView)

        var right = binding.right
        var left = binding.left
        val lesson = binding.lesson
        val desc = binding.desc
        right.visibility=View.INVISIBLE

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                var videoId = "wAv1lWKiqS8"
                youTubePlayer.loadVideo(videoId, 0f)


                left.setOnClickListener {
                    when (videoId) {
                        "wAv1lWKiqS8" -> {
                            videoId = "pihZjB_jsuY"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 2"
                            desc.text = "How to play Sa Re Ga Ma Pa on Harmonium"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "pihZjB_jsuY" -> {
                            lesson.text = "Lesson 3"
                            videoId = "8_v3Y5Nu-1M"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "Find your own Singing Scale "
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "8_v3Y5Nu-1M" -> {
                            videoId = "w6YNkD3v_M0"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Holding Notes Practice"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "w6YNkD3v_M0" -> {
                            videoId = "PnMcoV5xpXM"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 5"
                            desc.text = "What to do Before Morning Evening Riyaz"
                            left.visibility=View.INVISIBLE
                            right.visibility=View.VISIBLE
                        }
                    }
                }

                right.setOnClickListener {
                    when (videoId) {
                        "pihZjB_jsuY" -> {
                            videoId = "wAv1lWKiqS8"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 1"
                            desc.text = "Introduction Beginner to Advanced"
                            right.visibility=View.INVISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "8_v3Y5Nu-1M" -> {
                            videoId = "pihZjB_jsuY"
                            lesson.text = "Lesson 2"
                            youTubePlayer.loadVideo(videoId, 0f)

                            desc.text = "How to play Sa Re Ga Ma Pa on Harmonium"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "w6YNkD3v_M0" -> {
                            lesson.text = "Lesson 3"
                            videoId = "8_v3Y5Nu-1M"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "Find your own Singing Scale"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "PnMcoV5xpXM" -> {
                            videoId = "w6YNkD3v_M0"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Holding Notes Practice"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
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