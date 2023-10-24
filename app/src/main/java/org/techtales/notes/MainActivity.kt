package org.techtales.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.techtales.notes.Adapter.NotesAdapter
import org.techtales.notes.Database.NoteDatabase
import org.techtales.notes.Models.NoteViewModel
import org.techtales.notes.Models.Notes
import org.techtales.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NotesAdapter.NotesClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Notes

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

        if (result.resultCode == Activity.RESULT_OK){

            val note = result.data?.getSerializableExtra("note") as? Notes
            if (note != null){

                viewModel.updateNote(note)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //this for initializing the UI
        initUI()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this){list->

            list?.let{
                adapter.updateList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)
    }


    private fun initUI() {


        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = NotesAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

            if(result.resultCode == Activity.RESULT_OK){

                val note = result.data?.getSerializableExtra("note") as? Notes
                if (note != null){

                    viewModel.insertNote(note)
                }
            }
        }

        binding.fbAddNote.setOnClickListener{

            val intent = Intent(this, AddNote::class.java)
            getContent.launch(intent)

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    adapter.filterList(newText)
                }

                return true
            }
        })

    }

    override fun onitemClicked(note: Notes) {

        val intent = Intent(this@MainActivity, AddNote::class.java)
        intent.putExtra("current_note",note)
        updateNote.launch(intent)
    }

    override fun onlongitemClicked(note: Notes, cardView: CardView) {

        selectedNote = note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView){

        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener (this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.delete_note){

            viewModel.deleteNote(selectedNote)
            return true
        }

        return false
    }

}