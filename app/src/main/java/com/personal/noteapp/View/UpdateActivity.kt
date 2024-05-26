package com.personal.noteapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.personal.noteapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityUpdateBinding
    var currentId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar!!.title = "Update Note"

        mBinding.btnCancelUpdate.setOnClickListener {
            Toast.makeText(this, "Nothing updated", Toast.LENGTH_SHORT).show()
        }
        mBinding.btnSaveUpdate.setOnClickListener {
            updateNote()
        }
        getAndSetData()
    }

    fun updateNote() {
        val noteTitle: String = mBinding.etTitleUpdate.text.toString()
        val noteDescription: String = mBinding.etDescriptionUpdate.text.toString()

        val intent = Intent()
        intent.putExtra("updateTitle", noteTitle)
        intent.putExtra("updateDescription", noteDescription)
        setResult(RESULT_OK, intent)
        if (currentId==-1){
            intent.putExtra("noteID",currentId)
            setResult(RESULT_OK,intent)
            finish()
        }

    }

    fun getAndSetData() {
        val currentTitle = intent.getStringExtra("currentTitle")
        val currentDescription = intent.getStringExtra("currentDescription")
         currentId = intent.getIntExtra("currentID", -1)
// Set the data in the fields

        mBinding.etTitleUpdate.setText(currentTitle)
        mBinding.etDescriptionUpdate.setText(currentDescription)

    }
}