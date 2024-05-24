package com.example.musical.ui.tabla

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.databinding.FragmentTablaBinding
import com.example.musical.ui.harmonium.addNotes
import com.example.musical.ui.harmonium.recyclerView
import com.google.firebase.auth.FirebaseAuth
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

        var right = binding.right
        var left = binding.left
        val lesson = binding.lesson
        val desc = binding.desc
        right.visibility=View.INVISIBLE

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                var videoId = "zlljHJtVaPw"
                youTubePlayer.loadVideo(videoId, 0f)


                left.setOnClickListener {
                    when (videoId) {
                        "zlljHJtVaPw" -> {
                            videoId = "GmcfVozXPoc"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 2"
                            desc.text = "Basic Tabla Bols Playing Techniques"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "GmcfVozXPoc" -> {
                            lesson.text = "Lesson 3"
                            videoId = "AeRjwvT9Id4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "Concept of Khuli, Mudi and Kayeda"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "AeRjwvT9Id4" -> {
                            videoId = "GTow0z9Rp7E"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Practice of Dha Terekete Tak"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "GTow0z9Rp7E" -> {
                            videoId = "vW918hOHD9Q"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 5"
                            desc.text = "Practice based on Tere Kete Tak, Kat GheGhe Tete Kat"
                            left.visibility=View.INVISIBLE
                            right.visibility=View.VISIBLE
                        }
                    }
                }

                right.setOnClickListener {
                    when (videoId) {
                        "GmcfVozXPoc" -> {
                            videoId = "zlljHJtVaPw"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 1"
                            desc.text = "Basics of Tabla, Parts of Tabla, Important Tabla Bol"
                            right.visibility=View.INVISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "AeRjwvT9Id4" -> {
                            lesson.text = "Lesson 2"
                            youTubePlayer.loadVideo(videoId, 0f)
                            videoId = "GmcfVozXPoc"
                            desc.text = "Basic Tabla Bols Playing Techniques"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "GTow0z9Rp7E" -> {
                            videoId = "AeRjwvT9Id4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 3"
                            desc.text = "Concept of Khuli, Mudi and Kayeda"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "vW918hOHD9Q" -> {
                            videoId = "GTow0z9Rp7E"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Practice of Dha Terekete Takt"
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

        val collection = "Tabla"

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