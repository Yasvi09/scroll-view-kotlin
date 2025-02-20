package com.example.scrollview.ui.theme

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scrollview.R

class PasscodeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val passcodeKey = "APP_PASSCODE"
    private var name: String? = null
    private var email: String? = null
    private var profileImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode)

       init()
    }

    private fun init(){
        name = intent.getStringExtra("name")
        email = intent.getStringExtra("email")
        profileImageUrl = intent.getStringExtra("profileImageUrl")

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        val passcodeTitle = findViewById<TextView>(R.id.passcodeTitle)
        val passcodeSubtitle = findViewById<TextView>(R.id.passcodeSubtitle)
        val backArrow = findViewById<ImageView>(R.id.back_arrow)


        val savedPasscode = getSavedPasscode()

        if (savedPasscode == null) {
            passcodeTitle.text = "Set your passcode"
            passcodeSubtitle.text = "Use a 4-digit code"
        } else {
            passcodeTitle.text = "Enter your passcode"
            passcodeSubtitle.text = "Use your 4-digit code"
        }

        setupListeners(savedPasscode)

        backArrow.setOnClickListener { finish() }
    }

    private fun setupListeners(savedPasscode: String?) {
        val digit1 = findViewById<EditText>(R.id.pin1)
        val digit2 = findViewById<EditText>(R.id.pin2)
        val digit3 = findViewById<EditText>(R.id.pin3)
        val digit4 = findViewById<EditText>(R.id.pin4)
        val forgotCode = findViewById<TextView>(R.id.forgot_code)

        digit1.setOnKeyListener { _, _, _ -> if (digit1.text.length == 1) digit2.requestFocus(); false }
        digit2.setOnKeyListener { _, _, _ -> if (digit2.text.length == 1) digit3.requestFocus(); false }
        digit3.setOnKeyListener { _, _, _ -> if (digit3.text.length == 1) digit4.requestFocus(); false }

        digit4.setOnKeyListener { _, _, _ ->
            if (digit4.text.length == 1) {
                val enteredPasscode = "${digit1.text}${digit2.text}${digit3.text}${digit4.text}"

                if (savedPasscode == null) {

                    savePasscode(enteredPasscode)
                    showToast("Passcode set successfully")
                    navigateToHome()
                } else {

                    if (enteredPasscode == savedPasscode) {
                        showToast("Passcode correct")
                        navigateToHome()
                    } else {
                        showToast("Incorrect passcode")
                        clearPasscodeFields()
                    }
                }
            }
            false
        }

        forgotCode.setOnClickListener {
            showToast("Forgot Code Clicked")
        }
    }

    private fun clearPasscodeFields() {
        findViewById<EditText>(R.id.pin1).text.clear()
        findViewById<EditText>(R.id.pin2).text.clear()
        findViewById<EditText>(R.id.pin3).text.clear()
        findViewById<EditText>(R.id.pin4).text.clear()
        findViewById<EditText>(R.id.pin1).requestFocus()
    }

    private fun savePasscode(passcode: String) {
        sharedPreferences.edit().putString(passcodeKey, passcode).apply()
    }

    private fun getSavedPasscode(): String? {
        return sharedPreferences.getString(passcodeKey, null)
    }

    private fun navigateToHome() {

        val homeIntent = Intent(this, HomeActivity::class.java)
        name?.let { homeIntent.putExtra("name", it) }
        email?.let { homeIntent.putExtra("email", it) }
        profileImageUrl?.let { homeIntent.putExtra("profileImageUrl", it) }
        startActivity(homeIntent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
