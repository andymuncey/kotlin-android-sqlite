package com.tinyappco.deadlines

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinyappco.deadlines.databinding.ActivityAssignmentDetailsBinding

class AssignmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssignmentDetailsBinding

    private lateinit var dataManager: DataManager

    private lateinit var modules : List<Module>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataManager = DataManager(this)

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

        refreshSpinner()


    }

    private fun addModule(){

        val intent = Intent(this,ModuleDetailActivity::class.java)
        startActivity(intent)
    }

    private fun addAssignment() {
        val title = binding.etTitle.text.toString()
        val weight = binding.etWeight.text.toString().toInt()
        val date = binding.datePicker.date()
        val selectedModule = modules[binding.spinner.selectedItemPosition]
        val assignment = Assignment(null, title, weight, date, selectedModule)
        dataManager.add(assignment)
        finish()
    }

    private fun refreshSpinner(){
        modules = dataManager.allModules() //if you've used a different name for the dataManager, change the reference here (and in future code that uses this name)
        val adapter = ArrayAdapter<Module>(this, R.layout.simple_spinner_item, modules)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refreshSpinner()
    }
}