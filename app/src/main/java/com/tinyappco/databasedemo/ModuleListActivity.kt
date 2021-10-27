package com.tinyappco.databasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.tinyappco.databasedemo.databinding.ActivityModuleListBinding


class ModuleListActivity : AppCompatActivity() {

    private lateinit var modules : List<Module>
    private lateinit var dataManager: DataManager

    private lateinit var binding: ActivityModuleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuleListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dataManager = DataManager(this)

        refreshList()

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            val module = modules[position]
            val intent = Intent(this,ModuleDetailsActivity::class.java)
            intent.putExtra("module",module)
            startActivity(intent)
        }

        registerForContextMenu(binding.listView)

        title=getString(R.string.modules)
    }

    private fun refreshList(){
        modules = dataManager.allModules()
        binding.listView.adapter = ArrayAdapter<Module>(this,android.R.layout.simple_list_item_1,modules)
    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
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



    override fun onResume() {
        super.onResume()
        refreshList()
    }
}
