package com.example.scrollview.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.scrollview.R
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    lateinit var btnLogout:Button
    lateinit var auth: FirebaseAuth
    lateinit var ivProfile: ImageView
    lateinit var tvEmail: TextView
    lateinit var tvName:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        btnLogout = findViewById(R.id.btnLogout)
        ivProfile=findViewById(R.id.ivProfile)
        tvEmail=findViewById(R.id.tvEmail)
        tvName=findViewById(R.id.tvName)

        val name = intent.getStringExtra("name")
        val email=intent.getStringExtra("email")
        val profileImageUrl = intent.getStringExtra("profileImageUrl")

        tvName.text = name
        tvEmail.text=email
        if(!profileImageUrl.isNullOrEmpty()){
            Glide.with(this)
                .load(Uri.parse(profileImageUrl))
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}