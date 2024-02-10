package com.surajpal.a1_dictionary.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.surajpal.a1_dictionary.R

class Intro : AppCompatActivity() {

    private val SPLASH_SCREEN_DURATION = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Handler to navigate to the main activity after the splash screen duration
        Handler().postDelayed({
            // Start the main activity
            val intent = Intent(this, SavedWords::class.java)
            startActivity(intent)
            // Finish the splash screen activity
            finish()
        }, SPLASH_SCREEN_DURATION)
    }
}