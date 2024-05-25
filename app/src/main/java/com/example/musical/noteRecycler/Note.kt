package com.example.musical.noteRecycler

import com.google.firebase.Timestamp

data class Note(val note:String="", val timestamp: Timestamp = Timestamp.now(),val docId:String="")
