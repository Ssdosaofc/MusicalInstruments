package com.example.musical

import com.google.firebase.Timestamp

data class Note(val note:String="", val timestamp: Timestamp = Timestamp.now())
