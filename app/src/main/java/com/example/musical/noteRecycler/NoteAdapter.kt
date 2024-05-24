package com.example.musical.noteRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.musical.Note
import com.example.musical.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class NoteAdapter(val context: Context,
                  options: FirestoreRecyclerOptions<Note>,val collection: String
):
    FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(options) {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var noteText = itemView.findViewById<TextView>(R.id.notetext)
        var delete = itemView.findViewById<AppCompatImageButton>(R.id.delete)
        var timestamp = itemView.findViewById<TextView>(R.id.timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notelist_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(p0: NoteViewHolder, p1: Int, p2: Note) {
        p0.noteText.text = p2.note
        p0.timestamp.text = SimpleDateFormat("dd/MM/yyyy").format(p2.timestamp.toDate())

        p0.delete.setOnClickListener {v->

            val user= FirebaseAuth.getInstance().currentUser!!
            deleteNote(user, context, collection)
        }
    }

    fun deleteNote(user: FirebaseUser, context: Context, collection: String){
        val ref = FirebaseFirestore.getInstance().collection("Notes")
            .document(user.uid).collection(collection)

        ref.document().delete().addOnCompleteListener { p0 ->
            if (p0.isSuccessful) {
                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Note could not be deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}