package com.example.scrollview.ui.theme

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
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
    private val passcodeLength = 5

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
            passcodeSubtitle.text = "Use a ${passcodeLength}-digit code"
        } else {
            passcodeTitle.text = "Enter your passcode"
            passcodeSubtitle.text = "Use your ${passcodeLength}-digit code"
        }

        setupListeners(savedPasscode)

        backArrow.setOnClickListener { finish() }
    }

    private fun setupListeners(savedPasscode: String?) {
        val passcodeContainer = findViewById<LinearLayout>(R.id.passcodeContainer)
        val editTexts = mutableListOf<EditText>()

        for (i in 0 until passcodeLength) {
            val editText = EditText(this).apply {
                layoutParams = LinearLayout.LayoutParams(130, 130).apply {
                    if (i != 0) setMargins(16, 0, 0, 0)
                }
                inputType = InputType.TYPE_CLASS_NUMBER
                textSize = 24f
                gravity = android.view.Gravity.CENTER
                maxLines = 1
                id = View.generateViewId()
                background = getDrawable(R.drawable.passcode_box)
            }
            passcodeContainer.addView(editText)
            editTexts.add(editText)
        }

        for (i in 0 until passcodeLength) {
            editTexts[i].addTextChangedListener(object : android.text.TextWatcher {
                override fun afterTextChanged(s: android.text.Editable?) {
                    if (s?.length == 1) {
                        if (i < passcodeLength - 1) {
                            editTexts[i + 1].requestFocus()
                        } else {

                            val enteredPasscode = editTexts.joinToString("") { it.text.toString() }

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
                                    clearPasscodeFields(editTexts)
                                }
                            }
                        }
                    } else if (s?.length == 0 && i > 0) {
                        editTexts[i - 1].requestFocus()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }


    private fun clearPasscodeFields(editTexts:List<EditText>) {
        editTexts.forEach { it.text.clear() }
        editTexts.first().requestFocus()
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
