package com.example.musical.noteRecycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.musical.Note
import com.example.musical.R

class NoteAdapter(val context: Context, var notes:List<Note>):
    Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var noteText = itemView.findViewById<TextView>(R.id.notetext)
        var delete = itemView.findViewById<TextView>(R.id.delete)
        var timestamp = itemView.findViewById<TextView>(R.id.timestamp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}