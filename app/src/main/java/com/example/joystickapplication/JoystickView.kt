package com.example.joystickapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.View
import kotlin.math.min

/**
 * The class is responsible for displaying the joystick on the screen
 */
class JoystickView(context: Context) : View(context) {
    private val outerColor: Paint
    private val innerColor: Paint
    private val backgroundColor: Paint

    var centerX: Float = 0F; private set
    var centerY: Float = 0F; private set

    var currX: Float
    var currY: Float

    var outerRadius: Float; private set
    var innerRadius: Float; private set

    private var statusBarHeight: Int

//    var isOrientationChanged: Boolean

    /**
     * Setting the colors of the background and the joystick components
     */
    init {
        this.outerColor = Paint(Paint.ANTI_ALIAS_FLAG)
        this.outerColor.color = Color.GRAY
        this.outerColor.style = Paint.Style.FILL

        this.innerColor = Paint(Paint.ANTI_ALIAS_FLAG)
        this.innerColor.color = Color.rgb(244, 163, 0)
        this.innerColor.style = Paint.Style.FILL

        this.backgroundColor = Paint(Paint.ANTI_ALIAS_FLAG)
        this.backgroundColor.color = resources.getColor(R.color.background_color, context.theme)
        this.backgroundColor.style = Paint.Style.FILL

    }

    /**
     * Setting the joystick display values
     */
    init {
        val dm: DisplayMetrics = resources.displayMetrics
        this.centerX = (dm.widthPixels / 2).toFloat()
        this.currX = this.centerX
        this.centerY = (dm.heightPixels / 2).toFloat()
        this.currY = this.centerY

        this.outerRadius = min(dm.heightPixels, dm.widthPixels).toFloat() / 3
        this.innerRadius = this.outerRadius / 3

        this.statusBarHeight = this.getStatusBarHeight() * dm.density.toInt()

//        this.isOrientationChanged = true
    }

    /**
     * Get the height of the status bar that is displayed on the screen, if exists
     *
     * @return the height of the status bar
     */
    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelOffset(resourceId)
        }
        return 0
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.run {
            drawRect(0F, 0F, centerX * 2, centerY * 2, backgroundColor)
            drawCircle(centerX, centerY - statusBarHeight, outerRadius, outerColor)
            drawCircle(currX, currY - statusBarHeight, innerRadius, innerColor)
        }
    }

    /**
     * Placing the joystick in the center of the screen according to the orientation of the screen
     *
     * @param w    - the width of the current orientation
     * @param h    - the height of the current orientation
     * @param oldw - the width of the previous orientation
     * @param oldh - the height of the previous orientation
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // updating a flag that is used to get the updated center values
//        this.isOrientationChanged = true

        centerX = (w / 2F)
        currX = centerX
        centerY = (h + statusBarHeight) / 2F
        currY = centerY

        outerRadius = min(centerX, centerY) * 2 / 3
        innerRadius = outerRadius / 3

        super.onSizeChanged(w, h, oldw, oldh)
    }

//    fun getOuterJoystickArgs(): Array<Float> {
//        return arrayOf(centerX, centerY, outerRadius, innerRadius)
//    }
//
//    fun notifyArgsUpdated() {
//        this.isOrientationChanged = false
//    }
}
