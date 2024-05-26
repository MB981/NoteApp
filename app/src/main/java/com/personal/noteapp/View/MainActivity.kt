package com.personal.noteapp.View

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isNotEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personal.noteapp.Adapters.NoteAdapter
import com.personal.noteapp.Model.Note
import com.personal.noteapp.NoteApplication
import com.personal.noteapp.R
import com.personal.noteapp.ViewModel.NoteViewModel
import com.personal.noteapp.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var addActivityResultLanucher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLanucher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        register acitivity for result
        registerActivityResultLanucher()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this)
        recyclerView.adapter = noteAdapter


        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
        noteViewModel.myAllNotes.observe(this, Observer { notes ->
// update the UI
            noteAdapter.setNote(notes)
        })


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
                    or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)

    }

    private fun registerActivityResultLanucher() {
        addActivityResultLanucher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultAddNote ->
                    val resultCode = resultAddNote.resultCode
                    val data = resultAddNote.data
                    if (resultCode == RESULT_OK && data != null) {
                        val noteTitle: String = data.getStringExtra("title").toString()
                        val noteDescription: String = data.getStringExtra("description").toString()
//                Adding to the database
                        val note = Note(noteTitle, noteDescription)
                        noteViewModel.insert(note)

                    }

                })
        updateActivityResultLanucher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultUpdateNote ->


                    val resultCode = resultUpdateNote.resultCode
                    val data = resultUpdateNote.data
                    if (resultCode == RESULT_OK && data != null) {
                        val updatedTitle: String = data.getStringExtra("updateTitle").toString()
                        val updatedDescription: String =
                            data.getStringExtra("updateDescription").toString()
                        val noteId: Int = data.getIntExtra("noteID", -1)
                        val newNote = Note(updatedTitle, updatedDescription)
                        newNote.id = noteId
                        noteViewModel.update(newNote)



                    }

                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_note -> {
                val i = Intent(this, NoteAddActivity::class.java)
                addActivityResultLanucher.launch(i)
            }
            R.id.item_delete_all_notes -> {
                showDialogMessage()
            }
        }
        return true
    }

    fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Notes")
        dialogMessage.setMessage("If click yes all notes will delete, if you want to delete a specific note swipe left or right.")
        dialogMessage.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialogInterface, i ->

                dialogInterface.cancel()
            })

        dialogMessage.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialogInterface, i ->
                noteViewModel.deleteAllNotes()
            })
        dialogMessage.create().show()
    }

}