package com.example.scrollview.ui.theme

import android.content.Context
import android.content.SharedPreferences
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.scrollview.R

class PasscodeManager(private val context: Context, private val passcodeLength: Int) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    private val passcodeKey = "APP_PASSCODE"

    fun createPasscodeFields(passcodeContainer: LinearLayout, onPasscodeEntered: (String) -> Unit) {
        val editTexts = mutableListOf<EditText>()

        for (i in 0 until passcodeLength) {
            val editText = EditText(context).apply {
                layoutParams = LinearLayout.LayoutParams(130, 130).apply {
                    if (i != 0) setMargins(16, 0, 0, 0)
                }
                inputType = InputType.TYPE_CLASS_NUMBER
                textSize = 24f
                gravity = Gravity.CENTER
                maxLines = 1
                id = View.generateViewId()
                background = context.getDrawable(R.drawable.passcode_box)
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
                            onPasscodeEntered(enteredPasscode)
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

    fun clearPasscodeFields(editTexts: List<EditText>) {
        editTexts.forEach { it.text.clear() }
        editTexts.first().requestFocus()
    }

    fun getSavedPasscode(): String? {
        return sharedPreferences.getString(passcodeKey, null)
    }

    fun savePasscode(passcode: String) {
        sharedPreferences.edit().putString(passcodeKey, passcode).apply()
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}

