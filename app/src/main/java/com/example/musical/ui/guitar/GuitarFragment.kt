package com.example.musical.ui.guitar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.databinding.FragmentGalleryBinding
import com.example.musical.ui.harmonium.addNotes
import com.example.musical.ui.harmonium.recyclerView
import com.example.musical.ui.recorder.RecorderViewModel
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class GuitarFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pianoViewModel =
            ViewModelProvider(this).get(RecorderViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
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
                var videoId = "BBz-Jyr23M4"
                youTubePlayer.loadVideo(videoId, 0f)


                left.setOnClickListener {
                    when (videoId) {
                        "BBz-Jyr23M4" -> {
                            videoId = "6Jxz9F3CYuo"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 2"
                            desc.text = "EASY 2 CHORD SONG & LEAD GUITAR"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "6Jxz9F3CYuo" -> {
                            lesson.text = "Lesson 3"
                            videoId = "SV2ehlxGEFw"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "'Three Little Birds' Guitar Tutorial"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "SV2ehlxGEFw" -> {
                            videoId = "VK1Fe0mnXvE"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Your First Riff!"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "VK1Fe0mnXvE" -> {
                            videoId = "VCIsdvZheC8"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 5"
                            desc.text = "'Ooh La la' Rod Stewart & NEW Melody! "
                            left.visibility=View.INVISIBLE
                            right.visibility=View.VISIBLE
                        }
                    }
                }

                right.setOnClickListener {
                    when (videoId) {
                        "6Jxz9F3CYuo" -> {
                            videoId = "BBz-Jyr23M4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 1"
                            desc.text = "Absolute Beginner? Start Here!l"
                            right.visibility=View.INVISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "SV2ehlxGEFw" -> {
                            videoId = "6Jxz9F3CYuo"
                            lesson.text = "Lesson 2"
                            youTubePlayer.loadVideo(videoId, 0f)

                            desc.text = "EASY 2 CHORD SONG & LEAD GUITAR"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "VK1Fe0mnXvE" -> {
                            lesson.text = "Lesson 3"
                            videoId = "SV2ehlxGEFw"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "'Three Little Birds' Guitar Tutorial"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "VCIsdvZheC8" -> {
                            videoId = "VK1Fe0mnXvE"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Your First Riff!"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                    }
                }
            }
        })

        val addnote = binding.tick
        val note = binding.note
        val list = binding.list
        val user= FirebaseAuth.getInstance().currentUser!!

        val collection = "Guitar"

        recyclerView(user,list,requireContext(),collection)

        addnote.setOnClickListener{
            addNotes(requireContext(),collection,addnote,note,user)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}