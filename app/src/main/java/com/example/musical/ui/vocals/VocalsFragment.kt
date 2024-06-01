package com.example.musical.ui.vocals

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.musical.R
import com.example.musical.ViewPagerAdapter
import com.example.musical.databinding.FragmentVocalsBinding
import com.example.musical.noteRecycler.Note
import com.example.musical.noteRecycler.NoteAdapter
import com.example.musical.ui.harmonium.addNotes
import com.example.musical.ui.harmonium.recyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class VocalsFragment : Fragment() {

    private var _binding: FragmentVocalsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var noteAdapter:NoteAdapter
    private lateinit var viewPager:ViewPager
    private lateinit var layout: LinearLayout
    private lateinit var right: Button
    private lateinit var left: Button
    private lateinit var adapter:ViewPagerAdapter
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

        right = binding.right
        left = binding.left

        layout = binding.indicatorLayout
        viewPager = binding.linearLayout

        val collection = "Vocals"
        adapter = ViewPagerAdapter(collection,requireContext())

        viewPager.adapter = adapter

        right.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (getItem(0)>0){
                    viewPager.setCurrentItem(getItem(-1),true)
                }
            }

        })
        left.setOnClickListener(object : View.OnClickListener {
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
        val user= FirebaseAuth.getInstance().currentUser!!



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