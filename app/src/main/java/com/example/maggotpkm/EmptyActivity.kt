package com.example.maggotpkm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.maggotpkm.ui.container.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EmptyActivity : AppCompatActivity() {

    private lateinit var preference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        preference = SharedPreference(this)

        signIn()

    }

    private fun signIn() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            "maggotkitbwi2022@gmail.com",
            "MaggotKitBwi2022"
        ).addOnSuccessListener { result ->
            Log.d("LOGIN-USER", result.user?.uid.toString())
            preference.saveLogin(true)

            lifecycleScope.launch {
                delay(DELAY_SCREEN)
                startActivity(Intent(this@EmptyActivity, MainActivity::class.java))
                finishAffinity()
            }

        }.addOnFailureListener { exception ->

            exception.message?.let { Log.d("LOGIN-USER", it) }

            MaterialAlertDialogBuilder(this)
                .setMessage("Tidak ada koneksi internet")
                .setPositiveButton("Coba Lagi") { _, _ ->
                    signIn()
                }
                .setNegativeButton("Keluar") { _, _ ->
                    finish()
                }
                .show()
        }
    }

    companion object {
        private const val DELAY_SCREEN = 1000L
    }
}