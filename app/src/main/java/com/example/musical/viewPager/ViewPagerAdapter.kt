package com.example.musical.viewPager

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.musical.R
import com.example.musical.ZoomActivity
import com.example.musical.favourite.Video
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class ViewPagerAdapter(val collection: String,val context: Context): PagerAdapter() {
    val lesson = listOf("Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5")
    val videoID = listOf("2xtDFynxxbA", "nfju0uiRlu8", "0toFIyCVJaA", "dS62BFegwZA", "rw7JiQ1lFh4")
    val desc = listOf(
        "Introduction to Harmonium",
        "Types of Harmonium",
        "Mistakes to Avoid on हारमोनियम",
        "Naming Keys & Nomenclature",
        "Music Theory Explained"
    )
    val videoIDG = listOf(
        "BBz-Jyr23M4",
        "6Jxz9F3CYuo",
        "SV2ehlxGEFw",
        "VK1Fe0mnXvE",
        "VCIsdvZheC8"
    )
    val descG = listOf(
        "Introduction",
        "EASY 2 CHORD SONG & LEAD GUITAR",
        "'Three Little Birds' Guitar Tutorial",
        "Your First Riff!",
        "'Ooh La la' Rod Stewart & NEW Melody!"
    )
    val videoIDK = listOf(
        "ISBZoVhxHjk",
        "2JASyzvQWpM",
        "IVc7vJ_7MaM",
        "XzbFkBrcRn4",
        "Vl-VCmocaOs"
    )
    val descK = listOf(
        "Introduction",
        "Learn to Read Music in 15 minutes",
        "12 Piano Exercises for Beginners Recommended by Professional Pianists",
        "Top Beginner Piano Tips for Success",
        "A Thousand Years (Christina Perri) Easy Beginner Keyboard Piano Tutorial"
    )
    val videoIDP = listOf(
        "827jmswqnEA",
        "hTmjb9CtsTQ",
        "PeYSesLA5K4",
        "ZD_sAWAlWq0",
        "rUqKWy9SvhI"
    )
    val descP = listOf(
        "Introduction",
        "Interesting chord accompaniment patterns",
        "More important chords you should know",
        "What About the Left Hand?",
        "\"Strumming\" on the Piano?"
    )
    val videoIDT = listOf(
        "zlljHJtVaPw",
        "GmcfVozXPoc",
        "AeRjwvT9Id4",
        "GTow0z9Rp7E",
        "vW918hOHD9Q"
    )
    val descT = listOf(
        "Introduction",
        "Basic Tabla Bols Playing Techniques",
        "Concept of Khuli, Mudi and Kayeda",
        "Practice of Dha Terekete Tak",
        "Practice based on Tere Kete Tak, Kat GheGhe Tete Kat"
    )
    val videoIDV = listOf(
        "iPbCdOsrDK4",
        "k2pxLr13ve4",
        "3OaBwXc_fP4",
        "dBqnxJYKRqQ",
        "JdBUYDjITHE"
    )
    val descV = listOf(
        "Introduction to the Violin",
        "Parts of the violin",
        "Names of strings & other notes",
        "How and where to bow",
        "Learning the open string notes"
    )
    val videoIDVo = listOf(
        "wAv1lWKiqS8",
        "pihZjB_jsuY",
        "8_v3Y5Nu-1M",
        "w6YNkD3v_M0",
        "PnMcoV5xpXM"
    )
    val descVo = listOf(
        "Introduction",
        "How to play Sa Re Ga Ma Pa on Harmonium",
        "Find your own Singing Scale",
        "Holding Notes Practice",
        "What to do Before Morning Evening Riyaz"
    )

    override fun getCount(): Int {
        return when (collection) {
            "Harmonium" -> videoID.size
            "Guitar" -> videoIDG.size
            "Keyboard" -> videoIDK.size
            "Piano" -> videoIDP.size
            "Tabla" -> videoIDT.size
            "Violin" -> videoIDV.size
            "Vocals" -> videoIDVo.size
            else -> 0
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)

        val youTubePlayerView = view.findViewById<YouTubePlayerView>(R.id.tab1)
        val lessonSlider = view.findViewById<TextView>(R.id.lesson)
        val descSlider = view.findViewById<TextView>(R.id.desc)
        val favButton = view.findViewById<ImageButton>(R.id.fav)
        val zoomButton = view.findViewById<LinearLayout>(R.id.zoom)

        lessonSlider.text = lesson[position]
        val videoId: String
        val description: String

        when (collection) {
            "Harmonium" -> {
                videoId = videoID[position]
                description = desc[position]
            }

            "Guitar" -> {
                videoId = videoIDG[position]
                description = descG[position]
            }

            "Keyboard" -> {
                videoId = videoIDK[position]
                description = descK[position]
            }

            "Piano" -> {
                videoId = videoIDP[position]
                description = descP[position]
            }

            "Tabla" -> {
                videoId = videoIDT[position]
                description = descT[position]
            }

            "Violin" -> {
                videoId = videoIDV[position]
                description = descV[position]
            }

            "Vocals" -> {
                videoId = videoIDVo[position]
                description = descVo[position]
            }

            else -> {
                videoId = ""
                description = ""
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({youtubePlayer(youTubePlayerView, context, videoId, zoomButton)
        },500)

        descSlider.text = description

        val video = Video(collection,videoId, lessonSlider.text as String,description)

        if (firebaseAuth.currentUser != null) {
            checkIfFavourite(videoId,video,favButton)
        }
        favButton.setOnClickListener {
            toggleFavoriteStatus(video,context)
        }
        container.addView(view)
        return view
    }

    /*
    It seems like you're experiencing an issue where playing a video at a certain position also triggers
    the video at the next position to play in the background. This could happen due to the recycling mechanism
    of ViewPager, where it preloads adjacent views for smooth scrolling. To prevent this, you should stop the
    playback of the video when the view is destroyed.
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as ConstraintLayout
        val youTubePlayerView = view.findViewById<YouTubePlayerView>(R.id.tab1)
        youTubePlayerView.release()

        container.removeView(view)
    }


}

val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

fun openFullScreen(linearLayout: LinearLayout, context: Context, videoId:String){
    linearLayout.setOnClickListener{
        val intent = Intent(context, ZoomActivity::class.java)
        intent.putExtra("id",videoId)
        context.startActivity(intent)
    }
}
fun addToFavourite(context: Context,videoId: String,lesson:String,desc:String,collection: String){
    val video = Video(collection,videoId,lesson,desc)
    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(videoId)
        .setValue(video)
        .addOnSuccessListener {
            Toast.makeText(context,"Added to favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener{
            Toast.makeText(context,"Could not add to favourites", Toast.LENGTH_SHORT).show()
        }
}

fun removeFromFavourite(context: Context,videoId: String){
    ref.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(videoId).removeValue()
        .addOnSuccessListener {
            Toast.makeText(context,"Removed from favourites", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener{
            Toast.makeText(context,"Could not remove from favourites", Toast.LENGTH_SHORT).show()
        }
}

fun checkIfFavourite(videoId: String, video: Video, favButton: ImageButton) {
    val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    reference.child(firebaseAuth.uid.toString()).child("Favourites")
        .child(videoId)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val isInMyFavourite = dataSnapshot.exists()
                video.isFav = isInMyFavourite
                updateFavoriteButton(favButton,video)
                //notifyItemChanged(position)

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
}

fun updateFavoriteButton(favButton:ImageButton,video: Video) {

    if (video.isFav) {
        favButton.setImageResource(R.drawable.baseline_favorite_24_white)
    } else {
        favButton.setImageResource(R.drawable.baseline_favorite_border_24)
    }
}

fun toggleFavoriteStatus(video: Video, context: Context) {
    if (video.isFav) {
        removeFromFavourite(context, video.videoId)
    } else {
        addToFavourite(context,video.videoId,video.lesson,video.desc,video.collection)
    }

}
fun youtubePlayer(youTubePlayerView:YouTubePlayerView,context: Context,videoId: String,zoomButton:LinearLayout){
    youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.loadVideo(videoId, 0f)
            youTubePlayer.pause()
            openFullScreen(zoomButton, context, videoId)
        }
    })
}




