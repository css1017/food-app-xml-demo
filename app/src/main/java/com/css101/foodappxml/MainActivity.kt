package com.css101.foodappxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000) //todo get rid
        setTheme(R.style.Theme_FoodiesXmlDemo)
        setContentView(R.layout.activity_main)
    }
}