package org.techtales.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.techtales.notes.Models.Notes
import org.techtales.notes.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Notes
    private lateinit var old_note : Notes
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        try {
            old_note = intent.getSerializableExtra("current_note") as Notes
            binding.etTitle.setText(old_note.title)
            binding.etNote.setText(old_note.note)
            isUpdate = true
        }catch (e : Exception){

            e.printStackTrace()

        }

        binding.imgCheck.setOnClickListener{

            val title = binding.etTitle.text.toString()
            val note_des = binding.etNote.text.toString()

            if (title.isNotEmpty() || note_des.isNotEmpty()){

                val formatter = SimpleDateFormat("EEE, d MMM yyy HH:mm a")

                if (isUpdate){

                    note = Notes(
                        old_note.id, title,note_des,formatter.format(Date())
                    )
                }else{

                    note = Notes(
                        null,title,note_des,formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{

                Toast.makeText(this@AddNote, "Please enter some data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBackArrow.setOnClickListener{

            onBackPressed()

        }
    }
}