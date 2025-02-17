package com.example.scrollview.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.scrollview.R
import com.google.android.material.navigation.NavigationView

class BlankActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationview: NavigationView
    lateinit var cardView: CardView
    lateinit var telegramCard: CardView
    lateinit var youtubeCard: CardView
    lateinit var rateuscard:CardView
    lateinit var cashcard:CardView
    lateinit var taskcard:CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationview = findViewById(R.id.nav_view)
        cardView=findViewById(R.id.card1)
        telegramCard = findViewById(R.id.telegram)
        youtubeCard=findViewById(R.id.youtube)
        cashcard=findViewById(R.id.cd_cash)
        taskcard=findViewById(R.id.cd_task)
        rateuscard=findViewById(R.id.rateus)

        cardView.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        telegramCard.setOnClickListener {
            openTelegram()
        }
        youtubeCard.setOnClickListener {
            openYoutube()
        }
        rateuscard.setOnClickListener {
            openPlaystore()
        }
        cashcard.setOnClickListener {
            val intent=Intent(this,CashActivity::class.java)
            startActivity(intent)
        }
        taskcard.setOnClickListener {
            val intent=Intent(this,TaskActivity::class.java)
            startActivity(intent)
        }

        navigationview.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.openDrawer, R.string.closeDrawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationview.setNavigationItemSelectedListener(this)
        navigationview.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_share -> {
                    Log.d("MainActivity", "Share item clicked")
                    drawerLayout.closeDrawer(navigationview)
                    shareContent()
                    true
                }
                R.id.nav_rate -> {
                    drawerLayout.closeDrawer(navigationview)
                    openPlaystore()
                    true
                }
                R.id.nav_help -> {
                    drawerLayout.closeDrawer(navigationview)
                    sendEmail()
                    true


                }
                else -> {
                    drawerLayout.closeDrawer(navigationview)
                    true
                }
            }
        }
    }

    fun onDropdownClick(view: View) {
        drawerLayout.openDrawer(navigationview)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationview)) {
            drawerLayout.closeDrawer(navigationview)
        } else {
            super.onBackPressed()
        }
    }

    private fun shareContent() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this amazing content!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun openPlaystore(){
        val appPackageName = "com.whatsapp"
        try{
            val uri=Uri.parse("market://details?id=$appPackageName")
            val intent=Intent(Intent.ACTION_VIEW,uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        catch (e:Exception){
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    private fun sendEmail() {

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("tanuvejani05@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Help & Support Request")
                putExtra(Intent.EXTRA_TEXT, "Please describe your issue here.")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            }

        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openTelegram() {
        try {
            val telegramUri=Uri.parse("tg://resolve?domain=telegram")
            val intent=Intent(Intent.ACTION_VIEW,telegramUri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        catch (e:Exception){
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    private fun openYoutube(){
        try{
            val youtubeUri=Uri.parse("https://www.youtube.com")
            val intent=Intent(Intent.ACTION_VIEW,youtubeUri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        catch (e:Exception){
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return true
    }
}
