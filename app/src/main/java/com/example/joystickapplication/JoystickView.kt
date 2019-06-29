package com.example.joystickapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.View
import kotlin.math.min

class JoystickView(context: Context) : View(context) {
    private val outerColor: Paint
    private val innerColor: Paint
    private val backgroundColor: Paint

    var centerX: Float
        private set
    var centerY: Float
        private set

    var currX: Float
    var currY: Float

    var outerRadius: Float
        private set
    var innerRadius: Float
        private set

    private var statusBarHeight: Float

    private var isOrientationChanged: Boolean

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

    init {
        val dm: DisplayMetrics = resources.displayMetrics
        this.centerX = (dm.widthPixels / 2).toFloat()
        this.currX = this.centerX
        this.centerY = (dm.heightPixels / 2).toFloat()
        this.currY = this.centerY

        this.outerRadius = min(dm.heightPixels, dm.widthPixels).toFloat() / 3
        this.innerRadius = this.outerRadius / 3

        this.statusBarHeight = this.getStatusBarHeight() * dm.density

        this.isOrientationChanged = true
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelOffset(resourceId)
        }
        return 0
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawRect(0F, 0F, centerX * 2, centerY * 2, backgroundColor)
        canvas.drawCircle(centerX, centerY - statusBarHeight, outerRadius, outerColor)
        canvas.drawCircle(currX, currY - statusBarHeight, innerRadius, innerColor)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // updating a flag that is used to get the updated center values
        this.isOrientationChanged = true

        centerX = (w / 2F)
        currX = centerX
        centerY = (h + statusBarHeight) / 2
        currY = centerY

        outerRadius = min(centerX, centerY) * 2 / 3
        innerRadius = outerRadius / 3

        super.onSizeChanged(w, h, oldw, oldh)
    }

//    fun getCurrX(): Float = this.currX
//    fun getCurrY(): Float = this.currY
//    fun isOrientationChanged(): Boolean = this.isOrientationChanged

    fun getOuterJoystickArgs(): Array<Float> {
        return arrayOf(centerX, centerY, outerRadius, innerRadius)
    }

//    fun setCurrX(x: Float) {
//        this.currX = x
//    }

//    fun setCurrY(y: Float) {
//        this.currY = y
//    }

    fun notifyArgsUpdated() {
        this.isOrientationChanged = false
    }

}
