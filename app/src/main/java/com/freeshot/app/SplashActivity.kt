// File Path: app/src/main/java/com/freeshot/app/SplashActivity.kt
package com.freeshot.app

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashLogo: ImageView = findViewById(R.id.splashLogo)

        // Fade-in effect
        ObjectAnimator.ofFloat(splashLogo, "alpha", 0f, 1f).apply {
            duration = 1500
            start()
        }

        splashScope.launch {
            // Fetch configuration
            ConfigManager.fetchConfig()
            
            // Ensure a minimum display time for the splash screen
            delay(2000)
            
            // Navigate to Main Activity
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        splashScope.cancel()
    }
}
