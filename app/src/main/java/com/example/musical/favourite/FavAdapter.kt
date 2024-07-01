package com.example.musical.favourite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.musical.R
import com.example.musical.viewPager.checkIfFavourite
import com.example.musical.viewPager.firebaseAuth
import com.example.musical.viewPager.toggleFavoriteStatus

class FavAdapter(val context: Context, private var videos: List<Video>): Adapter<FavAdapter.FavViewHolder>() {
    //text.setPaintFlags(text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG)

    class FavViewHolder(itemView: View):ViewHolder(itemView){
        val lesson = itemView.findViewById<TextView>(R.id.lesson)
        var desc = itemView.findViewById<TextView>(R.id.desc)
        val openLink = itemView.findViewById<LinearLayout>(R.id.openLink)
        var instrument = itemView.findViewById<ImageView>(R.id.instrument)
        val favButton = itemView.findViewById<ImageButton>(R.id.favButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favlist_item, parent, false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val video = videos[position]

        val videoId = video.videoId
        holder.desc.text = video.desc
        holder.lesson.text = video.lesson

        when(video.collection){
            "Guitar" -> holder.instrument.setImageResource(R.drawable.guitar)
            "Keyboard" -> holder.instrument.setImageResource(R.drawable.key)
            "Piano" -> holder.instrument.setImageResource(R.drawable.piano_musical_instrument_svgrepo_com)
            "Violin" -> holder.instrument.setImageResource(R.drawable.violin_silhouette_svgrepo_com)
            "Tabla" -> holder.instrument.setImageResource(R.drawable.tablas_svgrepo_com)
            "Harmonium" -> holder.instrument.setImageResource(R.drawable.harmonium_svgrepo_com)
            "Vocals" -> holder.instrument.setImageResource(R.drawable.vocal)
        }
//https://stackoverflow.com/questions/15941732/start-intent-in-adapter

        holder.openLink.setOnClickListener {
            val intent = Intent(context, ExpandActivity::class.java)
            intent.putExtra("videoId",videoId)
            context.startActivity(intent)
        }

        val favButton = holder.favButton
        if (firebaseAuth.currentUser != null) {
            checkIfFavourite(videoId,video,favButton)
        }
        favButton.setOnClickListener {
            toggleFavoriteStatus(video,context)
        }

        holder.bindingAdapter
    }

    @Synchronized
    fun updateData(newList: List<Video>) {
        if (videos != newList) {
            videos = newList
            notifyDataSetChanged()
        }
    }
}