package com.example.joystickapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun startConnection(view: View): Unit {
        val ip = findViewById<EditText>(R.id.editTextIP).text.toString()
        val port = findViewById<EditText>(R.id.editTextPort).text.toString()

        val intent = Intent(this, ConnectionActivity::class.java)
        intent.putExtra("ip", ip)
        intent.putExtra("port", port)
        startActivity(intent)
    }
}
