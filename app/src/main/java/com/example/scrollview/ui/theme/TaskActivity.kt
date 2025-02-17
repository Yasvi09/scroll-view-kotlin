package com.example.scrollview.ui.theme

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scrollview.R

class TaskActivity : AppCompatActivity() {
    lateinit var backarrow: ImageView
    lateinit var ayetstudio:LinearLayout
    lateinit var adgate:LinearLayout
    lateinit var offertoro:LinearLayout
    lateinit var adgem :LinearLayout
    lateinit var tapjoy:LinearLayout
    lateinit var tasktitle:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        backarrow=findViewById(R.id.arrowback)
        ayetstudio=findViewById(R.id.ayet_studio)
        adgate=findViewById(R.id.adgate_media)
        offertoro=findViewById(R.id.offertoro)
        adgem=findViewById(R.id.adgem)
        tapjoy=findViewById(R.id.tapjoy)
        tasktitle=findViewById(R.id.headerLayout)
        tasktitle.text="Task"

        backarrow.setOnClickListener {
            finish()
        }

        ayetstudio.setOnClickListener {
            Toast.makeText(this, "You clicked Ayet Studio!", Toast.LENGTH_SHORT).show()
        }
        adgate.setOnClickListener {
            Toast.makeText(this, "You clicked AdGate Media!", Toast.LENGTH_SHORT).show()
        }
        offertoro.setOnClickListener {
            Toast.makeText(this, "You clicked Offertoro!", Toast.LENGTH_SHORT).show()
        }
        adgem.setOnClickListener {
            Toast.makeText(this, "You clicked Adgem!", Toast.LENGTH_SHORT).show()
        }
        tapjoy.setOnClickListener {
            Toast.makeText(this, "You clicked Tapjoy!", Toast.LENGTH_SHORT).show()
        }
    }
}