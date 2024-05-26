package com.personal.noteapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_table")
class Note(val title: String, val description: String) {
    @PrimaryKey(autoGenerate = true)
//    Id automatically generated
    var id = 0

}