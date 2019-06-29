package com.example.joystickapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import kotlin.math.sqrt


class JoystickActivity : AppCompatActivity() {

    private val client: Client = Client()
    private var joystickView: JoystickView
    private var isTouchingJoystick: Boolean

    init {
        this.joystickView = JoystickView(this)
        this.isTouchingJoystick = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this.joystickView)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event!!.action
        val touchX = event.x
        val touchY = event.y

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // if the touch happened outside the joystick then ignore it
                if (!this.isInsideJoystick(touchX, touchY)) {
                    return false
                }
                // otherwise, update the flag for upcoming move actions
                this.isTouchingJoystick = true
            }

            MotionEvent.ACTION_MOVE -> {
                if (!this.isTouchingJoystick) {
                    return false
                }
                // used to normalize the values
                val distance = this.distance(touchX, touchY, this.joystickView.centerX, this.joystickView.centerY)
                var magnitude = (distance + this.joystickView.innerRadius) / this.joystickView.outerRadius
                if (magnitude >= 1) {
                    magnitude = 1F
                }
                val angle = this.getAngle(
                    (touchX - this.joystickView.centerX).toDouble(),
                    (touchY - this.joystickView.centerY).toDouble()
                )
                val elevator: String = (Math.sin(Math.toRadians(angle)) * magnitude * -1).toString()
                val aileron: String = (Math.cos(Math.toRadians(angle)) * magnitude).toString()

                this.client.sendCommand("elevator", elevator)
                this.client.sendCommand("aileron", aileron)

                // draw the new position
                val newPos = this.getAdjustedPosition(touchX, touchY, angle, distance)
                this.joystickView.currX = newPos[0]
                this.joystickView.currY = newPos[1]
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.joystickView.currX = this.joystickView.centerX
                this.joystickView.currY = this.joystickView.centerY
                this.isTouchingJoystick = false
            }
        }
        return true
    }

    private fun isInsideJoystick(touchX: Float, touchY: Float): Boolean {
        return this.distance(
            touchX,
            touchY,
            this.joystickView.currX,
            this.joystickView.currY
        ) <= this.joystickView.innerRadius
    }

    private fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
    }

    private fun getAngle(dx: Double, dy: Double): Double {
        if (dx >= 0 && dy >= 0) return Math.toDegrees(Math.atan(dy / dx))
        else if (dx < 0 && dy >= 0) return Math.toDegrees(Math.atan(dy / dx)) + 180
        else if (dx < 0 && dy < 0) return Math.toDegrees(Math.atan(dy / dx)) + 180
        else if (dx >= 0 && dy < 0) return Math.toDegrees(Math.atan(dy / dx)) + 360
        return 0.0
    }

    private fun getAdjustedPosition(
        touchX: Float,
        touchY: Float,
        angle: Double,
        distanceFromCenter: Float
    ): Array<Float> {
        if (distanceFromCenter + this.joystickView.innerRadius <= this.joystickView.outerRadius) {
            return arrayOf(touchX, touchY)
        }
        val newX =
            this.joystickView.centerX + Math.cos(Math.toRadians(angle)) * (joystickView.outerRadius - joystickView.innerRadius)
        val newY =
            this.joystickView.centerY + Math.cos(Math.toRadians(angle)) * (joystickView.outerRadius - joystickView.innerRadius)
        return arrayOf(newX.toFloat(), newY.toFloat())
    }

    override fun onDestroy() {
        this.client.disconnect()
        super.onDestroy()
    }
}
