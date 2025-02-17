package com.example.scrollview.ui.theme

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.scrollview.R

class CashActivity : AppCompatActivity() {

    lateinit var backarrow:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        backarrow=findViewById(R.id.arrowback)
        backarrow.setOnClickListener {
            finish()
        }

    }
}