package com.example.musical.ui.harmonium

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.musical.R
import com.example.musical.viewPager.ViewPagerAdapter
import com.example.musical.databinding.FragmentHaarmoniumBinding
import com.example.musical.noteRecycler.Note
import com.example.musical.noteRecycler.NoteAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HarmoniumFragment : Fragment() {

    private lateinit var user: FirebaseUser

    private lateinit var noteAdapter:NoteAdapter
    private var _binding: FragmentHaarmoniumBinding? = null
    private lateinit var viewPager:ViewPager
    private lateinit var layout: LinearLayout
    private lateinit var right:Button
    private lateinit var left:Button
    private lateinit var adapter: ViewPagerAdapter
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

//        val youTubePlayerView = binding.tab1
//        lifecycle.addObserver(youTubePlayerView)

        right = binding.right
        left = binding.left
        right.visibility = View.INVISIBLE

//        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                var videoId = "2xtDFynxxbA"
//                youTubePlayer.loadVideo(videoId, 0f)
//
//
//                left.setOnClickListener {
//                    when (videoId) {
//                        "2xtDFynxxbA" -> {
//                            videoId = "nfju0uiRlu8"
//                            youTubePlayer.loadVideo(videoId, 0f)
//                            lesson.text = "Lesson 2"
//                            desc.text = "Types of Harmonium"
//                            right.visibility=View.VISIBLE
//                            left.visibility=View.VISIBLE
//                        }
//                        "nfju0uiRlu8" -> {
//                            lesson.text = "Lesson 3"
//                            videoId = "0toFIyCVJaA"
//                            youTubePlayer.loadVideo(videoId, 0f)
//                            desc.text = "Mistakes to Avoid on हारमोनियम "
//                            right.visibility=View.VISIBLE
//                            left.visibility=View.VISIBLE
//                        }
//                        "0toFIyCVJaA" -> {
//                            videoId = "dS62BFegwZA"
//                            youTubePlayer.loadVideo(videoId, 0f)
//                            lesson.text = "Lesson 4"
//                            desc.text = "Naming Keys & Nomenclature"
//                            right.visibility=View.VISIBLE
//                            left.visibility=View.VISIBLE
//                        }
//                        "dS62BFegwZA" -> {
//                            videoId = "rw7JiQ1lFh4"
//                            youTubePlayer.loadVideo(videoId, 0f)
//                            lesson.text = "Lesson 5"
//                            desc.text = "Music Theory Explained"
//                            left.visibility=View.INVISIBLE
//                            right.visibility=View.VISIBLE
//                        }
//                    }
//                }
//
//                right.setOnClickListener {
//                    when (videoId) {
//                        "nfju0uiRlu8" -> {
//                            videoId = "2xtDFynxxbA"
//                            youTubePlayer.loadVideo(videoId, 0f)
//                            lesson.text = "Lesson 1"
//                            desc.text = "Introduction to Harmonium"
//                            right.visibility=View.INVISIBLE
//                            left.visibility=View.VISIBLE
//                        }
//                        "0toFIyCVJaA" -> {
//                            videoId = "nfju0uiRlu8"
//                            lesson.text = "Lesson 2"
//                            youTubePlayer.loadVideo(videoId, 0f)
//
//                            desc.text = "Types of Harmonium"
//                            right.visibility=View.VISIBLE
//                            left.visibility=View.VISIBLE
//                        }
//                        "dS62BFegwZA" -> {
//                            lesson.text = "Lesson 3"
//                            videoId = "0toFIyCVJaA"
//                            youTubePlayer.loadVideo(videoId, 0f)
//                            desc.text = "Mistakes to Avoid on हारमोनियम"
//                            right.visibility=View.VISIBLE
//                            left.visibility=View.VISIBLE
//                        }
//                        "rw7JiQ1lFh4" -> {
//                            videoId = "dS62BFegwZA"
//                            youTubePlayer.loadVideo(videoId, 0f)
//                            lesson.text = "Lesson 4"
//                            desc.text = "Naming Keys & Nomenclature"
//                            right.visibility=View.VISIBLE
//                            left.visibility=View.VISIBLE
//                        }
//                    }
//                }
//
//                openFullScreen(zoom,requireContext(), videoId)
//            }
//        })
        val collection = "Harmonium"

        layout = binding.indicatorLayout
        viewPager = binding.linearLayout
        adapter = ViewPagerAdapter(collection,requireContext())

        viewPager.adapter = adapter

        right.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                if (getItem(0)>0){
                    viewPager.setCurrentItem(getItem(-1),true)
                }
            }

        })
        left.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                if (getItem(0)<4){
                    viewPager.setCurrentItem(getItem(1),true)

                }
            }

        })

        setupIndicator(0)
        viewPager.addOnPageChangeListener(viewListener)

        val addnote = binding.tick
        val note = binding.note
        val list = binding.list
        user= FirebaseAuth.getInstance().currentUser!!

        val ref = FirebaseFirestore.getInstance().collection("Notes")
            .document(user.uid).collection(collection)
        val query = ref.orderBy("timestamp",Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(query, Note::class.java).build()
        noteAdapter = NoteAdapter(requireContext(),options,collection)

        recyclerView(list, requireContext(), noteAdapter)

        addnote.setOnClickListener{
            addNotes(requireContext(),collection,addnote,note,user)
        }

        noteAdapter.notifyDataSetChanged()

        return root
    }

    fun setupIndicator(position:Int){
        val dots = arrayOfNulls<TextView?>(5)
        layout.removeAllViews()
        for (i in dots.indices){
            dots[i] = TextView(context)
            dots[i]?.text = Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY)
            dots[i]?.textSize = 30F
            dots[i]?.setTextColor(resources.getColor(R.color.inactive, context?.theme))
            layout.addView(dots[i])
        }
        dots[position]?.setTextColor(resources.getColor(R.color.brown, context?.theme))
    }

    private val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // No-op
        }

        override fun onPageSelected(position: Int) {
            setupIndicator(position)
            left.visibility = if (position < adapter.count - 1) View.VISIBLE else View.INVISIBLE
            right.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE
        }

        override fun onPageScrollStateChanged(state: Int) {
            // No-op
        }
    }

    fun getItem(i:Int): Int {
        return viewPager.currentItem + i
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewPager.adapter = null
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

            val ref = FirebaseFirestore.getInstance().collection("Notes")
                .document(user.uid).collection(collection)

            val docId= ref.document().id

            val noteSave  = Note(note.text.toString(), Timestamp.now(), docId)

            ref.document(docId).set(noteSave).addOnCompleteListener { p0 ->
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

fun recyclerView(
    list: RecyclerView,
    context: Context,
    noteAdapter: NoteAdapter
){

    list.layoutManager = LinearLayoutManager(context)
    list.adapter = noteAdapter
    noteAdapter.notifyDataSetChanged()
}



