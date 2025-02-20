package com.example.scrollview.ui.theme

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.scrollview.R

class PasscodeActivity : AppCompatActivity() {

    private lateinit var passcodeManager: PasscodeManager
    private var name: String? = null
    private var email: String? = null
    private var profileImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode)

        val passcodeLength = intent.getIntExtra("passcodeLength", 6)
        passcodeManager = PasscodeManager(this, passcodeLength)

        init(passcodeLength)
    }

    private fun init(passcodeLength: Int) {
        name = intent.getStringExtra("name")
        email = intent.getStringExtra("email")
        profileImageUrl = intent.getStringExtra("profileImageUrl")

        val passcodeTitle = findViewById<TextView>(R.id.passcodeTitle)
        val passcodeSubtitle = findViewById<TextView>(R.id.passcodeSubtitle)
        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        val passcodeContainer = findViewById<LinearLayout>(R.id.passcodeContainer)

        val savedPasscode = passcodeManager.getSavedPasscode()

        if (savedPasscode == null) {
            passcodeTitle.text = "Set your passcode"
            passcodeSubtitle.text = "Use a $passcodeLength-digit code"
        } else {
            passcodeTitle.text = "Enter your passcode"
            passcodeSubtitle.text = "Use your $passcodeLength-digit code"
        }

        passcodeManager.createPasscodeFields(passcodeContainer) { enteredPasscode ->
            handlePasscodeEntry(enteredPasscode, savedPasscode)
        }

        backArrow.setOnClickListener { finish() }
    }

    private fun handlePasscodeEntry(enteredPasscode: String, savedPasscode: String?) {
        if (savedPasscode == null) {
            passcodeManager.savePasscode(enteredPasscode)
            passcodeManager.showToast("Passcode set successfully")
            navigateToHome()
        } else {
            if (enteredPasscode == savedPasscode) {
                passcodeManager.showToast("Passcode correct")
                navigateToHome()
            } else {
                passcodeManager.showToast("Incorrect passcode")

                val passcodeContainer = findViewById<LinearLayout>(R.id.passcodeContainer)
                val editTexts = mutableListOf<EditText>()
                for (i in 0 until passcodeContainer.childCount) {
                    (passcodeContainer.getChildAt(i) as? EditText)?.let { editTexts.add(it) }
                }
                passcodeManager.clearPasscodeFields(editTexts)
            }
        }
    }


    private fun navigateToHome() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        name?.let { homeIntent.putExtra("name", it) }
        email?.let { homeIntent.putExtra("email", it) }
        profileImageUrl?.let { homeIntent.putExtra("profileImageUrl", it) }
        startActivity(homeIntent)
        finish()
    }
}


