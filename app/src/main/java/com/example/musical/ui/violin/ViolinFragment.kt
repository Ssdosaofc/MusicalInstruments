package com.example.musical.ui.violin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.noteRecycler.Note
import com.example.musical.databinding.FragmentViolinBinding
import com.example.musical.noteRecycler.NoteAdapter
import com.example.musical.ui.harmonium.addNotes
import com.example.musical.ui.harmonium.recyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class ViolinFragment : Fragment() {

    private var _binding: FragmentViolinBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var noteAdapter:NoteAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pianoViewModel =
            ViewModelProvider(this).get(ViolinViewModel::class.java)

        _binding = FragmentViolinBinding.inflate(inflater, container, false)
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
                var videoId = "iPbCdOsrDK4"
                youTubePlayer.loadVideo(videoId, 0f)


                left.setOnClickListener {
                    when (videoId) {
                        "iPbCdOsrDK4" -> {
                            videoId = "k2pxLr13ve4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 2"
                            desc.text = "Parts of the violin"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "2JASyzvQWpM" -> {
                            lesson.text = "Lesson 3"
                            videoId = "3OaBwXc_fP4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "Names of strings & other notes"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "3OaBwXc_fP4" -> {
                            videoId = "dBqnxJYKRqQ"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "How and where to bow"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "dBqnxJYKRqQ" -> {
                            videoId = "JdBUYDjITHE"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 5"
                            desc.text = "Learning the open string notes"
                            left.visibility=View.INVISIBLE
                            right.visibility=View.VISIBLE
                        }
                    }
                }

                right.setOnClickListener {
                    when (videoId) {
                        "k2pxLr13ve4" -> {
                            videoId = "iPbCdOsrDK4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 1"
                            desc.text = "How to hold the violin & bow"
                            right.visibility=View.INVISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "3OaBwXc_fP4" -> {
                            videoId = "k2pxLr13ve4"
                            lesson.text = "Lesson 2"
                            youTubePlayer.loadVideo(videoId, 0f)

                            desc.text = "Parts of the violin"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "dBqnxJYKRqQ" -> {
                            lesson.text = "Lesson 3"
                            videoId = "3OaBwXc_fP4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "Names of strings & other notes"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "JdBUYDjITHE" -> {
                            videoId = "dBqnxJYKRqQ"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "How and where to bow "
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

        val collection = "Violin"

        val ref = FirebaseFirestore.getInstance().collection("Notes")
            .document(user.uid).collection(collection)
        val query = ref.orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(query, Note::class.java).build()
        noteAdapter = NoteAdapter(requireContext(),options,collection)

        recyclerView(list, requireContext(), noteAdapter)

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