package com.example.musical.favourite

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FavRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val ref = FirebaseDatabase.getInstance().getReference("Users")
    private val userId = firebaseAuth.uid.toString()
    fun fetchFavo(callback: (List<Video>) -> Unit)
//    : LiveData<List<Video>>
    {
        val favoritesRef = ref.child(userId).child("Favourites")
//        val data = MutableLiveData<List<Video>>()
        favoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Video>()
//                list.clear()
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val video: Video? = dataSnapshot.getValue(Video::class.java)
                    video?.let { list.add(it) }
                }

//                data.value = list
                callback(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Repository","Change Failed")
            }

        })
//        return data
    }
}