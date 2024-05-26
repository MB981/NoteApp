package com.personal.noteapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.personal.noteapp.databinding.ActivityNoteAddBinding

class NoteAddActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityNoteAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNoteAddBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar!!.title = "Note Add"
        mBinding.btnCancel.setOnClickListener {
            Toast.makeText(this, "Nothing saved", Toast.LENGTH_SHORT).show()
            finish()
        }
        mBinding.btnSave.setOnClickListener {
            saveNote()
        }
    }

    fun saveNote() {
        val noteTitle: String = mBinding.etTitle.text.toString()
        val noteDescription: String = mBinding.etDescription.text.toString()

        val intent = Intent()
        intent.putExtra("title", noteTitle)
        intent.putExtra("description", noteDescription)
        setResult(RESULT_OK, intent)
        finish()
    }
}