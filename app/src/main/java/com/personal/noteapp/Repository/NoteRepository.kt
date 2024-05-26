package com.personal.noteapp.Repository

import androidx.annotation.WorkerThread
import com.personal.noteapp.Model.Note
import com.personal.noteapp.Room.NoteDAO
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDAO) {

    val myAllNotes: Flow<List<Note>> = noteDAO.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note) {
        noteDAO.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note) {
        noteDAO.update(note)
    }

    @WorkerThread
    suspend fun delete(note: Note) {
        noteDAO.delete(note)
    }

    @WorkerThread
    suspend fun deleteAllNotes() {
        noteDAO.deleteAllNotes()
    }


}