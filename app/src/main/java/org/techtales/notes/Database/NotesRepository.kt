package org.techtales.notes.Database

import androidx.lifecycle.LiveData
import org.techtales.notes.Models.Notes

class NotesRepository(private val noteDao: NoteDao) {

    val allNotes : LiveData<List<Notes>> = noteDao.getAllNotes()

    suspend fun insert(note: Notes){
        noteDao.insert(note)

    }

    suspend fun delete(note: Notes){
        noteDao.delete(note)
    }

    suspend fun update(note: Notes){
        noteDao.update(note.id,note.title,note.note)
    }



}