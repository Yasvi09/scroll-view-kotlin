package com.example.scrollview.ui.theme

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollview.R

class ReedemCardActivity : AppCompatActivity() {

    lateinit var reedemtitle:TextView
    lateinit var backarrow:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reedem_card)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        reedemtitle=findViewById(R.id.headerLayout)
        backarrow=findViewById(R.id.arrowback)

        backarrow.setOnClickListener {
            finish()
        }
        reedemtitle.text="Redeem"

        val redeemItems = listOf(
            RedeemItem(coins = "1200 🪙", amount = "Rs.10"),
            RedeemItem(coins = "5200 🪙", amount = "Rs.50")
        )


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RedeemAdapter(redeemItems)
    }
    }
