package com.tinyappco.deadlines


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinyappco.deadlines.databinding.ActivityAssignmentDetailsBinding
import java.util.Calendar

class AssignmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssignmentDetailsBinding

    private lateinit var dataManager: DataManager

    private lateinit var modules : List<Module>

    private var existingAssignment : Assignment? = null

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

        val assignment = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("assignment", Assignment::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("assignment")
        }
        if (assignment is Assignment){
            existingAssignment = assignment
            binding.etTitle.setText(assignment.title)
            binding.etWeight.setText(assignment.weight.toString())

            val cal = Calendar.getInstance()
            cal.time = assignment.deadline
            binding.datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null)

            binding.btnAdd.text = getString(R.string.update)
            title = getString(R.string.edit_assignment)
        } else {
            title = getString(R.string.add_assignment)
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

        val immutableExistingAssignment = existingAssignment

        if (immutableExistingAssignment != null) {
            immutableExistingAssignment.title = title
            immutableExistingAssignment.weight = weight
            immutableExistingAssignment.deadline = binding.datePicker.date()
            immutableExistingAssignment.module = selectedModule

            dataManager.update(immutableExistingAssignment)
        } else {
            val assignment = Assignment(null, title, weight, date, selectedModule)
            dataManager.add(assignment)
        }
        finish()
    }

    private fun refreshSpinner(){
        modules = dataManager.allModules() //if you've used a different name for the dataManager, change the reference here (and in future code that uses this name)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, modules)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refreshSpinner()
    }
}