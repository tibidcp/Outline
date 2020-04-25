package com.tibi.geodesy.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.tibi.geodesy.database.LinearObjectCoordinate
import com.tibi.geodesy.database.PointObjectCoordinate
import com.tibi.geodesy.draw.Draw
import com.tibi.geodesy.utils.selectCanvasObject

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

    var currentScale = 10f



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = w / 2f
        centerY = h / 2f


        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.scale(currentScale, currentScale, centerX, centerY)
        extraCanvas.translate(centerX, centerY)
        centerX -= centerX
        centerY -= centerY
        extraCanvas.drawColor(Color.WHITE)
        draw = Draw(extraCanvas)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    fun redraw() {
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
        redraw()
    }

    fun updatePoints(points: List<PointObjectCoordinate>) {
        this.points = points
        redraw()
    }

    fun select() : Any? {
        val x = centerX - width / 2f / currentScale + displayX / currentScale
        val y = - (centerY - height / 2f / currentScale + displayY / currentScale)
        val delta = 50f / currentScale
        return selectCanvasObject(x + delta, y + delta,
        x - delta, y - delta, points, lines)
    }

    fun initDrawing() {
        extraCanvas.translate(centerX, centerY)
        centerX -= centerX
        centerY -= centerY
        redraw()
    }

    fun highlightPoint(point: PointObjectCoordinate) {
        point.color = Color.RED
        redraw()
    }

    fun unHighlightAll() {
        points.forEach { it.color = Color.BLACK }
        lines.forEach { it.color = Color.BLACK }
        redraw()
    }

    fun highlightLine(line: LinearObjectCoordinate) {
        lines.filter { it.linearObjectId == line.linearObjectId }
            .forEach { it.color = Color.RED }
        redraw()
    }
}