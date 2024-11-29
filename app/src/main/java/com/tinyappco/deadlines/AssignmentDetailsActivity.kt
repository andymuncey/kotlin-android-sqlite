package com.tinyappco.deadlines

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinyappco.deadlines.databinding.ActivityAssignmentDetailsBinding

class AssignmentDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAssignmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAssignmentDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAdd.setOnClickListener {
            addAssignment()
        }

        binding.btnAddModule.setOnClickListener{
            addModule()
        }


    }

    private fun addModule(){

        val intent = Intent(this,ModuleDetailActivity::class.java)
        startActivity(intent)
    }

    private fun addAssignment() {

    }
}