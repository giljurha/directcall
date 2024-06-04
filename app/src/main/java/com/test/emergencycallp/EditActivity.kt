package com.test.emergencycallp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.test.emergencycallp.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthdateLayout.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthdayTextView.text = "$year -${month.inc()} -$dayOfMonth"
            }
            DatePickerDialog(
                this, listener, 2000, 1, 1
            ).show()
        }

        binding.warningCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.warningEditText.isVisible = isChecked
        }

        binding.warningEditText.isVisible = binding.warningCheckBox.isChecked
        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData() {
        val editor = getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()
        with(editor) {
            putString(NAME, binding.nameEditText.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY_CONTACT, binding.emergencyNumberEditText.text.toString())
            putString(BIRTHDATE, binding.birthdayTextView.text.toString())
            putString(WARNING, getWarning())
            apply()
        }
        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    private fun getWarning(): String {
        return if(binding.warningCheckBox.isChecked) binding.warningEditText.text.toString() else ""
    }
}