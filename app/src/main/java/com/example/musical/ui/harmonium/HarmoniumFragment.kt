package com.example.musical.ui.harmonium

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musical.Note
import com.example.musical.databinding.FragmentHaarmoniumBinding
import com.example.musical.noteRecycler.NoteAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class HarmoniumFragment : Fragment() {

    private lateinit var user: FirebaseUser

    private lateinit var noteAdapter:NoteAdapter
    private var _binding: FragmentHaarmoniumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pianoViewModel =
            ViewModelProvider(this).get(HarmoniumViewModel::class.java)

        _binding = FragmentHaarmoniumBinding.inflate(inflater, container, false)
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
                var videoId = "2xtDFynxxbA"
                youTubePlayer.loadVideo(videoId, 0f)


                left.setOnClickListener {
                    when (videoId) {
                        "2xtDFynxxbA" -> {
                            videoId = "nfju0uiRlu8"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 2"
                            desc.text = "Types of Harmonium"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "nfju0uiRlu8" -> {
                            lesson.text = "Lesson 3"
                            videoId = "0toFIyCVJaA"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "Mistakes to Avoid on हारमोनियम "
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "0toFIyCVJaA" -> {
                            videoId = "dS62BFegwZA"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Naming Keys & Nomenclature"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "dS62BFegwZA" -> {
                            videoId = "rw7JiQ1lFh4"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 5"
                            desc.text = "Music Theory Explained"
                            left.visibility=View.INVISIBLE
                            right.visibility=View.VISIBLE
                        }
                    }
                }

                right.setOnClickListener {
                    when (videoId) {
                        "nfju0uiRlu8" -> {
                            videoId = "2xtDFynxxbA"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 1"
                            desc.text = "Introduction to Harmonium"
                            right.visibility=View.INVISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "0toFIyCVJaA" -> {
                            videoId = "nfju0uiRlu8"
                            lesson.text = "Lesson 2"
                            youTubePlayer.loadVideo(videoId, 0f)

                            desc.text = "Types of Harmonium"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "dS62BFegwZA" -> {
                            lesson.text = "Lesson 3"
                            videoId = "0toFIyCVJaA"
                            youTubePlayer.loadVideo(videoId, 0f)
                            desc.text = "Mistakes to Avoid on हारमोनियम"
                            right.visibility=View.VISIBLE
                            left.visibility=View.VISIBLE
                        }
                        "rw7JiQ1lFh4" -> {
                            videoId = "dS62BFegwZA"
                            youTubePlayer.loadVideo(videoId, 0f)
                            lesson.text = "Lesson 4"
                            desc.text = "Naming Keys & Nomenclature"
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
        user= FirebaseAuth.getInstance().currentUser!!

        val collection = "Harmonium"

        val ref = FirebaseFirestore.getInstance().collection("Notes")
            .document(user.uid).collection(collection)
        val query = ref.orderBy("timestamp",Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(query,Note::class.java).build()
        noteAdapter = NoteAdapter(requireContext(),options,collection)

        recyclerView(user,list,requireContext(),collection,noteAdapter)

        addnote.setOnClickListener{
            addNotes(requireContext(),collection,addnote,note,user)
        }

        noteAdapter.notifyDataSetChanged()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        noteAdapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        noteAdapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.notifyDataSetChanged()
    }

    }
fun addNotes(context: Context, collection: String, addNote:FloatingActionButton, note:AppCompatEditText, user: FirebaseUser) {
    addNote.setOnClickListener{

        if(note.text.isNullOrEmpty()){
            Toast.makeText(context,"Note is empty",Toast.LENGTH_SHORT).show()
        }
        else{
            val noteSave  = Note(note.text.toString(), Timestamp.now())


            val ref = FirebaseFirestore.getInstance().collection("Notes")
                .document(user.uid).collection(collection)

            ref.document().set(noteSave).addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
                    note.text!!.clear()
                } else {
                    Toast.makeText(context, "Note could not be added", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

fun recyclerView(user: FirebaseUser,harmoList:RecyclerView,context: Context,collection: String,noteAdapter: NoteAdapter){

    harmoList.layoutManager = LinearLayoutManager(context)
    harmoList.adapter = noteAdapter
    noteAdapter.notifyDataSetChanged()
}