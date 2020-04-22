package com.tibi.geodesy.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.tibi.geodesy.database.LinearObjectCoordinate
import com.tibi.geodesy.database.PointObjectCoordinate
import com.tibi.geodesy.draw.Draw

private const val SCALE_FACTOR = 1.25f

class MyCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private lateinit var draw: Draw

    var motionTouchEventX = 0f
    var motionTouchEventY = 0f

    private var displayX = 0f
    private var displayY = 0f

    private var centerX = 0f
    private var centerY = 0f

    private var lines = listOf<LinearObjectCoordinate>()
    private var points = listOf<PointObjectCoordinate>()

    var currentScale = 1f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = w / 2f
        centerY = h / 2f


        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(Color.WHITE)
        draw = Draw(extraCanvas)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    private fun redraw() {
        extraCanvas.drawColor(Color.WHITE)
        draw.drawAllPoint(points)
        draw.drawAllLinear(lines)
        invalidate()
    }

    fun zoomIn() {
        currentScale *= SCALE_FACTOR
        extraCanvas.scale(SCALE_FACTOR, SCALE_FACTOR, centerX, centerY)
        redraw()
    }

    fun zoomOut() {
        currentScale /= SCALE_FACTOR
        extraCanvas.scale(1f / SCALE_FACTOR, 1f / SCALE_FACTOR, centerX, centerY)
        redraw()
    }

    fun touchStart() {
        displayX = motionTouchEventX
        displayY = motionTouchEventY
        redraw()
    }

    fun touchMove() {
        val dx = motionTouchEventX - displayX
        val dy = motionTouchEventY - displayY
        extraCanvas.translate(dx / currentScale, dy / currentScale)

        displayX = motionTouchEventX
        displayY = motionTouchEventY
        centerX -= dx / currentScale
        centerY -= dy / currentScale
        redraw()
    }

    fun updateLines(lines: List<LinearObjectCoordinate>) {
        this.lines = lines
    }

    fun updatePoints(points: List<PointObjectCoordinate>) {
        this.points = points
    }

    fun select() {
        Log.d("Coordinates", "X = ${centerX - width / 2 / currentScale + displayX / currentScale}, " +
                "Y = ${centerY - height / 2 / currentScale + displayY / currentScale}")
    }
}