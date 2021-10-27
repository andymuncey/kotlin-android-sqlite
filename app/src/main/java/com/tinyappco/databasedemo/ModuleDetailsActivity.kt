package com.tinyappco.databasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tinyappco.databasedemo.databinding.ActivityModuleDetailsBinding


class ModuleDetailsActivity : AppCompatActivity() {

    private var existingModule : Module? = null
    private lateinit var dataManager : DataManager

    private lateinit var binding : ActivityModuleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuleDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dataManager = DataManager(this)

        val module = intent.getSerializableExtra("module")
        if (module is Module){
            existingModule = module
            binding.etTitle.setText(module.name)
            binding.etCode.setText(module.code)
            binding.etCode.isEnabled = false //cant change code as primary key - delete module instead
            binding.button.text = getString(R.string.update)
            title = "Edit ${module.code}"
        } else {
            title = "Add module"
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun addModule(v: View){

        val immutableExistingModule = existingModule
        if (immutableExistingModule != null){
            immutableExistingModule.name = binding.etTitle.text.toString()
            dataManager.update(immutableExistingModule)
            finish()
        } else {

            val code = binding.etCode.text.toString()
            if (validateModuleCode(code)) {
                val module = Module(binding.etCode.text.toString(), binding.etTitle.text.toString())
                val result = dataManager.add(module)

                if (result) {

                    finish()
                } else {
                    binding.tvError.text = getString(R.string.module_exists)
                }

            } else {
                binding.tvError.text = getString(R.string.invalid_module_code)
            }
        }
    }

    private fun validateModuleCode(code: String) : Boolean{
        val regEx = "([A-Z]{2})([4-7])([0-9]{3})"
        return code.matches(Regex(regEx))
    }

}
