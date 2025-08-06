package com.example.gettingstartedwithjetpackcompose.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "notes",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        //onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)], //when user is deleted, delete all notes
    indices = [Index(value = ["userId"])]
)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)