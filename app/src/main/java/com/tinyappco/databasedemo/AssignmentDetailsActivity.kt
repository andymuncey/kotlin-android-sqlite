package com.tinyappco.databasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.tinyappco.databasedemo.databinding.ActivityAssignmentDetailsBinding
import java.util.*

class AssignmentDetailsActivity : AppCompatActivity() {

    private lateinit var dataMgr : DataManager
    private lateinit var modules : List<Module>

    private var existingAssignment : Assignment? = null

    private lateinit var binding: ActivityAssignmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dataMgr = DataManager(this)



        val assignment = intent.getSerializableExtra("assignment")
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

        binding.btnAdd.setOnClickListener { addAssignment() }

    }



    override fun onStart() {
        super.onStart()
        binding.btnAdd.isEnabled = dataMgr.hasModules()
    }

    private fun refreshSpinner(){
        modules = dataMgr.allModules()
        val adapter = ArrayAdapter<Module>(this,android.R.layout.simple_spinner_item, modules)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        val assignment = existingAssignment
        if (assignment != null){
            binding.spinner.setSelection(adapter.getPosition(assignment.module))
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun addModule(v: View){
        val intent = Intent(this,ModuleDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        refreshSpinner()
    }

    @Suppress("UNUSED_PARAMETER")
    fun addAssignment(){

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

            dataMgr.update(immutableExistingAssignment)
        } else {
            val assignment = existingAssignment ?: Assignment(null, title, weight, date, selectedModule)
            dataMgr.add(assignment)
        }

        finish()
    }
}
