package com.example.musical.ui.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.databinding.FragmentSlideshowBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class KeyboardFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val keyboardViewModel =
            ViewModelProvider(this).get(KeyboardViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
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
                var videoId = "ISBZoVhxHjk"
                youTubePlayer.loadVideo(videoId, 0f)


                left.setOnClickListener {
                    when (videoId) {
                        "ISBZoVhxHjk" -> {
                            videoId = "2JASyzvQWpM"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 2"
                            desc.text = "Learn to Read Music in 15 minutes"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "2JASyzvQWpM" -> {
                            lesson.text = "Lesson 3"
                            videoId = "IVc7vJ_7MaM"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "12 Piano Exercises for Beginners Recommended by Professional Pianists"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "IVc7vJ_7MaM" -> {
                            videoId = "XzbFkBrcRn4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Top Beginner Piano Tips for Success "
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "XzbFkBrcRn4" -> {
                            videoId = "Vl-VCmocaOs"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 5"
                            desc.text = "A Thousand Years (Christina Perri) Easy Beginner Keyboard Piano Tutorial"
                            left.visibility=View.INVISIBLE
                            right.visibility=View.VISIBLE
                        }
                    }
                }

                right.setOnClickListener {
                    when (videoId) {
                        "2JASyzvQWpM" -> {
                            videoId = "ISBZoVhxHjk"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 1"
                            desc.text = "Getting Started"
                            right.visibility=View.INVISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "IVc7vJ_7MaM" -> {
                            videoId = "2JASyzvQWpM"
                            lesson.text = "Lesson 2"
                            youTubePlayer.loadVideo(videoId, 0f)

                            desc.text = "Learn to Read Music in 15 minutes"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "XzbFkBrcRn4" -> {
                            lesson.text = "Lesson 3"
                            videoId = "IVc7vJ_7MaM"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "12 Piano Exercises for Beginners Recommended by Professional Pianists"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "Vl-VCmocaOs" -> {
                            videoId = "XzbFkBrcRn4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Top Beginner Piano Tips for Success "
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