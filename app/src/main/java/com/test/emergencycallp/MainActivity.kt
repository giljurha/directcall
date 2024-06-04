package com.test.emergencycallp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.test.emergencycallp.databinding.ActivityEditBinding
import com.test.emergencycallp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
        binding.deleteButton.setOnClickListener {
            deleteData()
        }
        binding.emergencyContractLayer.setOnClickListener {
            val intent = with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.emergencyNumberValueTextView.text.toString()
                    .replace("-","")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataAndUiUpdate()
    }

    private fun getDataAndUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            with(binding) {
                nameValueTextView.text = getString(NAME, "미정")
                birthdayValueTextView.text = getString(BIRTHDATE, "미정")
                bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
                emergencyNumberValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
                val warning = getString(WARNING, "")

                warningTextView.isVisible = warning.isNullOrEmpty().not()
                warningValueTextView.isVisible = warning.isNullOrEmpty().not()

                if (warning.isNullOrEmpty()) {
                    binding.warningValueTextView.text = getString(WARNING, "미정")
                }
            }
        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear()
            apply()
            getDataAndUiUpdate()
        }
        getDataAndUiUpdate()
        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
}