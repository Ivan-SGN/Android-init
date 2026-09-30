package com.example.android_init

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_init.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.second_activity_title)
            setDisplayHomeAsUpEnabled(true)
        }

        val text = intent.getStringExtra(EXTRA_TEXT)
        binding.receivedTextView.text =
            if (text.isNullOrBlank()) getString(R.string.no_text_received) else text
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_TEXT = "com.example.android_init.EXTRA_TEXT"
    }
}
