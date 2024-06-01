package com.example.musical

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
        "Introduction to Harmonium",
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
        "Introduction to Harmonium",
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
        "Introduction to Harmonium",
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
        "Introduction to Harmonium",
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
        "Introduction to Harmonium",
        "How to play Sa Re Ga Ma Pa on Harmonium",
        "Find your own Singing Scale",
        "Holding Notes Practice",
        "What to do Before Morning Evening Riyaz"
    )

    override fun getCount(): Int {
        return videoID.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout,container,false)

        val youTubePlayerView = view.findViewById<YouTubePlayerView>(R.id.tab1)
        val lessonSlider = view.findViewById<TextView>(R.id.lesson)
        val descSlider = view.findViewById<TextView>(R.id.desc)
        val favButton = view.findViewById<ImageButton>(R.id.fav)
        val zoomButton = view.findViewById<LinearLayout>(R.id.zoom)

        lessonSlider.setText(lesson[position])
        when(collection){
            "harmonium"->{
                descSlider.setText(desc[position])
            }
            "guitar"->{
                descSlider.setText(descG[position])
            }
            "keyboard"->{
                descSlider.setText(descK[position])
            }
            "piano"->{
                descSlider.setText(descP[position])
            }
            "tabla"->{
                descSlider.setText(descT[position])
            }
            "violin"->{
                descSlider.setText(descV[position])
            }
            "vocals"->{
                descSlider.setText(descVo[position])
            }

        }

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                when(collection){
                    "harmonium"->{
                        youTubePlayer.loadVideo(videoID[position], 0f)
                        openFullScreen(zoomButton,context,videoID[position])
                    }
                    "guitar"->{
                        youTubePlayer.loadVideo(videoIDG[position], 0f)
                        openFullScreen(zoomButton,context,videoIDG[position])
                    }
                    "keyboard"->{
                        youTubePlayer.loadVideo(videoIDK[position], 0f)
                        openFullScreen(zoomButton,context,videoIDK[position])
                    }
                    "piano"->{
                        youTubePlayer.loadVideo(videoIDP[position], 0f)
                        openFullScreen(zoomButton,context,videoIDP[position])
                    }
                    "tabla"->{
                        youTubePlayer.loadVideo(videoIDT[position], 0f)
                        openFullScreen(zoomButton,context,videoIDT[position])
                    }
                    "violin"->{
                        youTubePlayer.loadVideo(videoIDV[position], 0f)
                        openFullScreen(zoomButton,context,videoIDV[position])
                    }
                    "vocals"->{
                        youTubePlayer.loadVideo(videoIDVo[position], 0f)
                        openFullScreen(zoomButton,context,videoIDVo[position])
                    }
                }
            }

        })

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}

data class video(val videoId: String="",val lesson:String="",val desc:String="",var isFav:Boolean=false)

val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

fun openFullScreen(linearLayout: LinearLayout, context: Context, videoId:String){
    linearLayout.setOnClickListener{
        val intent = Intent(context,ZoomActivity::class.java)
        intent.putExtra("id",videoId)
        context.startActivity(intent)
    }
}
fun addToFavourite(context: Context,videoId: String,lesson:String,desc:String){

    val video = video(videoId,lesson,desc)
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



