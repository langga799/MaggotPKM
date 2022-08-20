package com.example.maggotpkm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.maggotpkm.databinding.ActivitySplashFullscreenBinding
import com.example.maggotpkm.ui.container.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashFullscreenBinding
    private lateinit var preference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = SharedPreference(this)

        when(preference.getLogin()){
            true -> {
                lifecycleScope.launch {
                    delay(DELAY_SCREEN)
                    startActivity(Intent(this@SplashFullscreenActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
            false -> {
                lifecycleScope.launch {
                    delay(DELAY_SCREEN)
                    startActivity(Intent(this@SplashFullscreenActivity, EmptyActivity::class.java))
                    finishAffinity()
                }
            }
        }


    }


    companion object {
        private const val DELAY_SCREEN = 1000L
    }


}