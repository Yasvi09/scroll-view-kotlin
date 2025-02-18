package com.example.scrollview.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.biometric.BiometricPrompt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.example.scrollview.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    lateinit var tvCaptcha: TextView
    lateinit var etCaptcha: EditText
    lateinit var tvLast : TextView
    lateinit var etemail: EditText
    lateinit var etpassword: EditText
    lateinit var btnLogin: AppCompatButton
    private var generatedCaptcha: String = ""

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 9001

    private lateinit var biometricExecutor : Executor
    private lateinit var biometricPrompt :BiometricPrompt
    private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        tvCaptcha = findViewById(R.id.tv_captcha)
        etCaptcha = findViewById(R.id.et_captcha)
        tvLast = findViewById(R.id.tv_last)
        etemail = findViewById(R.id.et_email)
        etpassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)

        generatedCaptcha = generateCaptcha()
        tvCaptcha.text = generatedCaptcha

        btnLogin.setOnClickListener {
            val email = etemail.text.toString()
            val password = etpassword.text.toString()
            val captchaInput=etCaptcha.text.toString()

            if (ValidateEmailAndPassword(email, password) && ValidateCaptcha(captchaInput)) {
                startBiometricAuthentication()
            } else {
                Toast.makeText(this, "Please fix the errors", Toast.LENGTH_SHORT).show()
            }
        }

        val text = getText(R.string.tv_last)
        val purpleColor = ContextCompat.getColor(this, R.color.purple_500)

        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
            }
        }
        val start = text.indexOf("sign up")
        val end = start + "sign up".length
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(purpleColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvLast.text = spannableString
        tvLast.movementMethod = LinkMovementMethod.getInstance()

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val continueWithGmailButton: CardView = findViewById(R.id.cd_email)
        continueWithGmailButton.setOnClickListener {
            signInWithGoogle()
        }

        biometricExecutor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, biometricExecutor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "Authentication succeeded!", Toast.LENGTH_SHORT).show()


                // Save login state
                val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                // Proceed to next activity after successful authentication
                val intent = Intent(this@LoginActivity, PasscodeActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })


        biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Use your fingerprint to login")
            .setNegativeButtonText("Cancel")
            .build()
    }


    @SuppressLint("RestrictedApi")
    private fun startBiometricAuthentication() {
        val fingerprintManager = FingerprintManagerCompat.from(this)

        if (!fingerprintManager.isHardwareDetected) {
            Toast.makeText(this, "Fingerprint hardware not detected", Toast.LENGTH_SHORT).show()
            return
        }

        biometricPrompt.authenticate(biometricPromptInfo)
    }
    private fun generateCaptcha() : String{
        val chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        return (1..6).map { chars.random() }.joinToString("")
    }

    private fun ValidateCaptcha(inputCaptcha: String): Boolean {
        return if (TextUtils.isEmpty(inputCaptcha)) {
            etCaptcha.error = "CAPTCHA cannot be empty"
            false
        } else if (inputCaptcha != generatedCaptcha) {
            etCaptcha.error = "CAPTCHA does not match"
            etCaptcha.postDelayed({
                refreshCaptcha()
            }, 500)
            false
        } else {
            true
        }
    }


    private fun refreshCaptcha(){
        generatedCaptcha=generateCaptcha()
        tvCaptcha.text=generatedCaptcha
        etCaptcha.text.clear()
    }

    private fun ValidateEmailAndPassword(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            etemail.error = "Email cannot be empty"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.error = "Please Enter a Valid Email"
            return false
        } else if (TextUtils.isEmpty(password)) {
            etpassword.error = "Password cannot be empty"
            return false
        } else if (password.length < 6) {
            etpassword.error = "Password must be at least 6 characters long"
            return false
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$".toRegex())) {
            etpassword.error = "Password must contain at least one uppercase letter, one lowercase letter, and one digit"
            return false
        }
        return true
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Sign-in failed", e)
                Toast.makeText(this, "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val name = user?.displayName
                    val email = user?.email
                    val profileImageUrl = user?.photoUrl.toString()

                    Toast.makeText(this, "Welcome ${user?.displayName}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, PasscodeActivity::class.java).apply {
                        putExtra("name", name)
                        putExtra("email", email)
                        putExtra("profileImageUrl", profileImageUrl)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
