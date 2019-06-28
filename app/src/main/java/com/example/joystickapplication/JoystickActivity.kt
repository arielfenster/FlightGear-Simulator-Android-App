package com.example.joystickapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class JoystickActivity : AppCompatActivity() {

    private val client : Client
    private var joystickView : JoystickView
    private var isTouchingJoystick : Boolean


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.joystickView = JoystickView(this)
        setContentView(this.joystickView)



    }


}
