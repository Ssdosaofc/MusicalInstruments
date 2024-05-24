package com.example.musical.ui.piano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.Note
import com.example.musical.databinding.FragmentPianoBinding
import com.example.musical.noteRecycler.NoteAdapter
import com.example.musical.ui.harmonium.addNotes
import com.example.musical.ui.harmonium.recyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class PianoFragment : Fragment() {

    private var _binding: FragmentPianoBinding? = null

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
            ViewModelProvider(this).get(PianoViewModel::class.java)

        _binding = FragmentPianoBinding.inflate(inflater, container, false)
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
                var videoId = "827jmswqnEA"
                youTubePlayer.loadVideo(videoId, 0f)


                left.setOnClickListener {
                    when (videoId) {
                        "827jmswqnEA" -> {
                            videoId = "hTmjb9CtsTQ"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 2"
                            desc.text = "Interesting chord accompaniment patterns"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "hTmjb9CtsTQ" -> {
                            lesson.text = "Lesson 3"
                            videoId = "PeYSesLA5K4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "More important chords you should know"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "PeYSesLA5K4" -> {
                            videoId = "ZD_sAWAlWq0"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "What About the Left Hand?"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "ZD_sAWAlWq0" -> {
                            videoId = "rUqKWy9SvhI"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 5"
                            desc.text = "\"Strumming\" on the Piano? "
                            left.visibility=View.INVISIBLE
                            right.visibility=View.VISIBLE
                        }
                    }
                }

                right.setOnClickListener {
                    when (videoId) {
                        "hTmjb9CtsTQ" -> {
                            videoId = "827jmswqnEA"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 1"
                            desc.text = "Getting Started! Learn some simple chords"
                            right.visibility=View.INVISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "PeYSesLA5K4" -> {
                            videoId = "hTmjb9CtsTQ"
                            lesson.text = "Lesson 2"
                            youTubePlayer.loadVideo(videoId, 0f)

                            desc.text = "Interesting chord accompaniment patterns"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "ZD_sAWAlWq0" -> {
                            lesson.text = "Lesson 3"
                            videoId = "PeYSesLA5K4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "More important chords you should know"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "rUqKWy9SvhI" -> {
                            videoId = "ZD_sAWAlWq0"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "What About the Left Hand?"
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

        val collection = "Piano"

        val ref = FirebaseFirestore.getInstance().collection("Notes")
            .document(user.uid).collection(collection)
        val query = ref.orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(query, Note::class.java).build()
        noteAdapter = NoteAdapter(requireContext(),options,collection)

        recyclerView(user,list,requireContext(),collection,noteAdapter)

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