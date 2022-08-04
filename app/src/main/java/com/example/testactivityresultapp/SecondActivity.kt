package com.example.testactivityresultapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.testactivityresultapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySecondBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_second
        )
        binding.apply {
            enterResultText.doAfterTextChanged {
                buttonGetResult.isEnabled = !it.isNullOrEmpty()
            }

            buttonGetResult.setOnClickListener {
                val result = Intent().putExtra(
                    SecondActivityContract.RESULT_KEY,
                    enterResultText.text.toString().toIntOrNull()
                )
                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }
    }
}