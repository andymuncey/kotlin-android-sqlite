package com.tinyappco.deadlines

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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

        registerForContextMenu(binding.listView)

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val module = modules[position]
            val intent = Intent(this,ModuleDetailActivity::class.java)
            intent.putExtra("module",module)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.menu_modules_context,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_module_delete){

            val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val module = modules[info.position]
            dataManager.delete(module)
            refreshList()
            return true
        }

        return super.onContextItemSelected(item)
    }

    private fun refreshList(){
        modules = dataManager.allModules()
        binding.listView.adapter = ArrayAdapter<Module>(this,
            android.R.layout.simple_list_item_1,modules)
    }
}