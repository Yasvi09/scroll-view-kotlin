package com.example.scrollview.ui.theme

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.scrollview.R

class   MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({

            val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

            if (isLoggedIn) {

                val name = sharedPreferences.getString("name", "")
                val email = sharedPreferences.getString("email", "")
                val profileImageUrl = sharedPreferences.getString("profileImageUrl", "")

                val intent = Intent(this, PasscodeActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("email", email)
                    putExtra("profileImageUrl", profileImageUrl)
                }
                startActivity(intent)
            } else {

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 1000)

    }
}
