package com.example.musical.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavViewModel:ViewModel() {
    private val repository = FavRepository()
    private val mvideos = MutableLiveData<List<Video>>()

    val videos: LiveData<List<Video>> get() = mvideos

    fun fetchFav() {
        repository.fetchFavo { videoList ->
            mvideos.postValue(videoList)
        }
    }
}

