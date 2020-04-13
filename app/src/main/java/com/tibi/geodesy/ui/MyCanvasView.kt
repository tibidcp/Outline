package com.tibi.geodesy.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
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
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 12f
    }
    private val path = Path()

    var motionTouchEventX = 0f
    var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f

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

    fun drawLine() {
        path.reset()
        path.moveTo(300f, 300f)
        path.lineTo(350f, 350f)
        extraCanvas.drawPath(path, paint)
        invalidate()
    }

    fun drawLine2() {
        path.reset()
        path.moveTo(400f, 400f)
        path.lineTo(450f, 450f)
        extraCanvas.drawPath(path, paint)
        invalidate()
    }

    private fun redraw() {
        extraCanvas.drawColor(Color.WHITE)
        draw.drawAllPoint(points)
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
        currentX = motionTouchEventX
        currentY = motionTouchEventY
        redraw()
    }

    fun touchMove() {
        val dx = motionTouchEventX - currentX
        val dy = motionTouchEventY - currentY
        extraCanvas.translate(dx / currentScale, dy / currentScale)

        currentX = motionTouchEventX
        currentY = motionTouchEventY
        centerX -= dx / currentScale
        centerY -= dy / currentScale
        redraw()
    }

    fun changeColor() {
        if (paint.color == Color.BLACK) {
            paint.color = Color.RED
        } else {
            paint.color = Color.BLACK
        }
        redraw()

    }

    fun updateLines(lines: List<LinearObjectCoordinate>) {
        this.lines = lines
    }

    fun updatePoints(points: List<PointObjectCoordinate>) {
        this.points = points
    }
}