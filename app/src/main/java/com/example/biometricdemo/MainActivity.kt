package com.example.biometricdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Set the title to display.")
            .setSubtitle("Set the subtitle to display.")
            .setDescription("Set the description to display")
            .setNegativeButtonText("Negative Button")
            .build()


        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                runOnUiThread { Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show() }

            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                runOnUiThread {
                    Toast.makeText(applicationContext,"Recognized",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,Success::class.java))
                }

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                runOnUiThread { Toast.makeText(applicationContext,"Not recognized",Toast.LENGTH_SHORT).show() }

            }
        })

        biometricPrompt.authenticate(promptInfo)
    }
}
