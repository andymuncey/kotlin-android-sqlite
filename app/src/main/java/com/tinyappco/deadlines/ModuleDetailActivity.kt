package com.tinyappco.deadlines

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinyappco.deadlines.databinding.ActivityModuleDetailBinding

class ModuleDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityModuleDetailBinding

    private lateinit var dataManager : DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataManager = DataManager(this)

        enableEdgeToEdge()

        binding = ActivityModuleDetailBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener { addModule() }
    }

    private fun addModule(){
        val code = binding.etCode.text.toString()
        if (validateModuleCode(code)) {
            val module = Module(binding.etCode.text.toString(), binding.etTitle.text.toString())
            if (dataManager.add(module)) {
                finish()
            } else {
                binding.tvError.text =
                    getString(R.string.unable_to_add_module_does_the_module_already_exist)
            }
        }else {
            binding.tvError.text =
                getString(R.string.module_code_should_be_two_upper_case_letters_followed_by_four_numbers)
        }
    }

    private fun validateModuleCode(code: String) : Boolean{
        val regEx = "([A-Z]{2})([4-7])([0-9]{3})"
        return code.matches(Regex(regEx))
    }
}