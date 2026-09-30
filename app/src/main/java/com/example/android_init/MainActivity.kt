package com.example.android_init

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.android_init.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.inputEditText.doAfterTextChanged { binding.inputLayout.error = null }

        binding.openSecondButton.setOnClickListener { openSecondActivity() }
        binding.callButton.setOnClickListener { dialPhoneNumber() }
        binding.shareButton.setOnClickListener { shareText() }
    }

    private fun openSecondActivity() {
        val text = getValidatedText() ?: return

        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra(SecondActivity.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }

    private fun dialPhoneNumber() {
        val phone = getValidatedText() ?: return

        if (!Patterns.PHONE.matcher(phone).matches()) {
            binding.inputLayout.error = getString(R.string.error_invalid_phone)
            return
        }

        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.error_no_dialer, Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareText() {
        val text = getValidatedText() ?: return

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_chooser_title)))
    }

    private fun getValidatedText(): String? {
        val text = binding.inputEditText.text?.toString()?.trim().orEmpty()
        if (text.isEmpty()) {
            binding.inputLayout.error = getString(R.string.error_empty_text)
            return null
        }
        return text
    }
}
