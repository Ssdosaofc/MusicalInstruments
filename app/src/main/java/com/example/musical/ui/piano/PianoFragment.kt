package com.example.musical.ui.piano

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.musical.R
import com.example.musical.viewPager.ViewPagerAdapter
import com.example.musical.databinding.FragmentPianoBinding
import com.example.musical.noteRecycler.Note
import com.example.musical.noteRecycler.NoteAdapter
import com.example.musical.ui.harmonium.addNotes
import com.example.musical.ui.harmonium.recyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PianoFragment : Fragment() {

    private var _binding: FragmentPianoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var noteAdapter:NoteAdapter
    private lateinit var viewPager:ViewPager
    private lateinit var layout: LinearLayout
    private lateinit var right: Button
    private lateinit var left: Button
    private lateinit var adapter: ViewPagerAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPianoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        right = binding.right
        left = binding.left
        right.visibility = View.INVISIBLE

        layout = binding.indicatorLayout
        viewPager = binding.linearLayout

        val collection = "Piano"
        adapter = ViewPagerAdapter(collection, requireContext())

        viewPager.adapter = adapter

        left.setOnClickListener {
            if (getItem(1) < adapter.count) {
                viewPager.setCurrentItem(getItem(1), true)
            }
        }

        right.setOnClickListener {
            if (getItem(-1) >= 0) {
                viewPager.setCurrentItem(getItem(-1), true)
            }
        }

        setupIndicator(0)
        viewPager.addOnPageChangeListener(viewListener)

        val addnote = binding.tick
        val note = binding.note
        val list = binding.list
        val user = FirebaseAuth.getInstance().currentUser!!

        val ref = FirebaseFirestore.getInstance().collection("Notes")
            .document(user.uid).collection(collection)
        val query = ref.orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(query, Note::class.java).build()
        noteAdapter = NoteAdapter(requireContext(), options, collection)

        recyclerView(list, requireContext(), noteAdapter)

        addnote.setOnClickListener {
            addNotes(requireContext(), collection, addnote, note, user)
        }
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

    val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            return
        }

        override fun onPageSelected(position: Int) {
            setupIndicator(position)
            if (position>0){
                right.visibility = View.VISIBLE
            }else{
                right.visibility = View.INVISIBLE
            }

            if (position<4){
                left.visibility = View.VISIBLE
            }else{
                left.visibility = View.INVISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            return
        }

    }

    fun getItem(i:Int): Int {
        return viewPager.currentItem + i
    }
}