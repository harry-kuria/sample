package com.example.gettingstartedwithjetpackcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes"
    //indices = [Index(value = ["title"], unique = true)]
)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)