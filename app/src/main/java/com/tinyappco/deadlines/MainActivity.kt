package com.tinyappco.deadlines

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinyappco.deadlines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var dataManager: DataManager
    private lateinit var dataSet : List<Assignment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataManager = DataManager(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        refreshDeadlines()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAddAssignment.setOnClickListener { addAssignment() }

        registerForContextMenu(binding.listView)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val component = dataSet[info.position]
            dataManager.delete(component)
            refreshDeadlines()
            return true
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        refreshDeadlines()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_deadines_context, menu)
    }


    fun addAssignment(){
        val intent = Intent(this,AssignmentDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_edit_modules){
            val intent = Intent(this,ModuleListActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshDeadlines(){
        dataSet = dataManager.assignments()
        binding.listView.adapter = ArrayAdapter<Assignment>(this,android.R.layout.simple_list_item_1,dataSet)
    }
}