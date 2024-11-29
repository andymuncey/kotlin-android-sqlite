package com.tinyappco.deadlines

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinyappco.deadlines.databinding.ActivityModuleListBinding

class ModuleListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityModuleListBinding

    private lateinit var modules : List<Module>
    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataManager = DataManager(this)



        enableEdgeToEdge()

        binding = ActivityModuleListBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        refreshList()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun refreshList(){
        modules = dataManager.allModules()
        binding.listView.adapter = ArrayAdapter<Module>(this,
            R.layout.simple_list_item_1,modules)
    }
}