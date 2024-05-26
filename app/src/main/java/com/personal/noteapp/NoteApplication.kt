package com.personal.noteapp

import android.app.Application
import com.personal.noteapp.Repository.NoteRepository
import com.personal.noteapp.Room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


//For make an instance for both Database and repository class

class NoteApplication : Application() {

    val applicationScope= CoroutineScope(SupervisorJob())
    val database by lazy { NoteDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { NoteRepository(database.getNoteDao()) }

}