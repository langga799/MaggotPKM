package com.example.maggotpkm.ui.container

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.maggotpkm.R
import com.example.maggotpkm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView = binding.bottomNavigation
        val navController = findNavController(R.id.fragment_container)
        navView.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        if (backPressedTime + 2000L > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            return
        } else {
            backToast = Toast.makeText(baseContext, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT);
            backToast?.show()
        }

        backPressedTime = System.currentTimeMillis()


    }


}