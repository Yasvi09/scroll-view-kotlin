package com.example.scrollview.ui.theme

import Transaction
import TransactionAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollview.R

class SecondActivity : AppCompatActivity() {

    private lateinit var transactionButton: Button
    private lateinit var redeemButton: Button
    private lateinit var transactionRecyclerView: RecyclerView
    private lateinit var backArrow: ImageView
    private lateinit var reedemcard:Button

    private lateinit var transactionAdapter: TransactionAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        transactionButton = findViewById(R.id.transaction)
        redeemButton = findViewById(R.id.redeem)
        transactionRecyclerView = findViewById(R.id.transaction_recycler_view)
        backArrow = findViewById(R.id.arrowback)
        reedemcard=findViewById(R.id.reedem_card_button)

        reedemcard.setOnClickListener {
            val intent=Intent(this,ReedemCardActivity::class.java)
            startActivity(intent)
        }

        backArrow.setOnClickListener {
            finish()
        }

        transactionRecyclerView.layoutManager = LinearLayoutManager(this)

        transactionAdapter = TransactionAdapter(emptyList())
        transactionRecyclerView.adapter = transactionAdapter

        showTransactionHistory()

        transactionButton.setOnClickListener {
            showTransactionHistory()
        }

        redeemButton.setOnClickListener {
            showRedeemHistory()
        }
    }

    private fun showTransactionHistory() {

        val transactionList = listOf(
            Transaction("Sign Up Bonus", "01 April 2023", "200", "Coins"),
            Transaction("Sign Up Bonus", "28 March 2023", "240", "Coins"),
            Transaction("Sign Up Bonus", "22 March 2023", "260", "Coins")
        )

        transactionAdapter.updateData(transactionList)

        transactionButton.setBackgroundResource(R.drawable.active_button_background)
        redeemButton.setBackgroundResource(R.drawable.inactive_button_background)
        redeemButton.setTextColor(ContextCompat.getColor(this,R.color.gray))
        transactionButton.setTextColor(ContextCompat.getColor(this,R.color.black))

    }

    private fun showRedeemHistory() {
        val redeemList = listOf(
            Transaction("Redeem Coins", "15 April 2023", "100", "Coins"),
            Transaction("Gift Card Purchase", "10 April 2023", "300", "Coins"),
            Transaction("Discount Coupon", "05 April 2023", "150", "Coins")
        )

        transactionAdapter.updateData(redeemList)

        redeemButton.setBackgroundResource(R.drawable.active_button_background)
        transactionButton.setBackgroundResource(R.drawable.inactive_button_background)
        transactionButton.setTextColor(ContextCompat.getColor(this,R.color.gray))
        redeemButton.setTextColor(ContextCompat.getColor(this,R.color.black))

    }
}
