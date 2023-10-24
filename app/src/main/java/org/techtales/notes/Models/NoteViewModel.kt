package org.techtales.notes.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.techtales.notes.Database.NoteDatabase
import org.techtales.notes.Database.NotesRepository

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val repository: NotesRepository

    var allnotes : LiveData<List<Notes>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NotesRepository(dao)
        allnotes = repository.allNotes
    }

    fun deleteNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(notes)
    }

    fun insertNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(notes)
    }

    fun updateNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(notes)
    }
}
