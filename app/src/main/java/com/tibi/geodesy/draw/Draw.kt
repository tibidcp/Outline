package com.tibi.geodesy.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.tibi.geodesy.database.LinearObjectCoordinate
import com.tibi.geodesy.database.PointObjectCoordinate
import com.tibi.geodesy.database.PointType
import java.lang.IllegalArgumentException

class Draw (canvas: Canvas) {
    private val paint = Paint()
    private val path = Path()
    private val rectF = RectF()
    private val canvas = canvas

    fun drawAllPoint(points: List<PointObjectCoordinate>) {
        points.forEach { point ->
            val x = point.x
            val y = point.y
            initPaintPath(point)
            drawText(point)
            when (point.type) {
                PointType.STATION.name -> drawStation(x, y)
                PointType.STONE_LIGHT.name -> drawStoneLight(x, y)
                PointType.METAL_LIGHT.name -> drawMetalLight(x, y)
                PointType.STONE_POST.name -> drawStonePost(x, y)
                PointType.METAL_POST.name -> drawMetalPost(x, y)
                PointType.TRAFFIC_LIGHT.name -> drawTrafficLight(x, y)
                PointType.WELL.name -> drawWell(x, y)
                PointType.GRID.name -> drawGrid(x, y)
                PointType.KILOMETER_SIGN.name -> drawKilometerSign(x, y)
                PointType.BUS_STOP_SIGN.name -> drawBusStopSign(x, y)
                PointType.POINTER_SIGN.name -> drawPointerSign(x, y)
                PointType.ROAD_SIGN.name -> drawRoadSign(x, y)
                PointType.BUSH.name -> drawBush(x, y)
                PointType.TREE.name -> drawTree(x, y)
                PointType.CONIFER.name -> drawConifer(x, y)
                PointType.POINT.name -> drawPoint(x, y)
                else -> throw IllegalArgumentException("Wrong point object type")
            }
        }
    }

    fun drawAllLinear(lines: List<LinearObjectCoordinate>) {

    }

    private fun drawStation(x: Float, y: Float) {
        path.moveTo(x, y - 17.3f)
        path.lineTo(x + 15f, y + 8.66f)
        path.lineTo(x - 15f, y + 8.66f)
        path.close()
        path.addCircle(x, y, 2f, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawStoneLight(x: Float, y: Float) {
        path.addCircle(x, y, 0.5f, Path.Direction.CW)
        path.addCircle(x, y, 5f, Path.Direction.CW)
        path.moveTo(x, y - 5)
        path.lineTo(x, y - 30)
        path.moveTo(x, y - 27)
        path.lineTo(x + 15, y - 27)
        path.moveTo(x + 7, y - 27)
        path.lineTo(x + 7, y - 17)
        path.moveTo(x + 12, y - 27)
        path.lineTo(x + 12, y - 17)

        rectF.set(x + 7, y - 20, x + 12, y - 14)
        path.addArc(rectF, 0f, 180f)

        canvas.drawPath(path, paint)
    }

    private fun drawMetalLight(x: Float, y: Float) {
        var r = 0.5f
        while (r <= 5f) {
            path.addCircle(x, y, r, Path.Direction.CW)
            r += 0.5f
        }

        path.moveTo(x, y - 5)
        path.lineTo(x, y - 30)
        path.moveTo(x, y - 27)
        path.lineTo(x + 15, y - 27)
        path.moveTo(x + 7, y - 27)
        path.lineTo(x + 7, y - 17)
        path.moveTo(x + 12, y - 27)
        path.lineTo(x + 12, y - 17)

        rectF.set(x + 7, y - 20, x + 12, y - 14)
        path.addArc(rectF, 0f, 180f)

        canvas.drawPath(path, paint)
    }

    private fun drawStonePost(x: Float, y: Float) {
        path.addCircle(x, y, 0.5f, Path.Direction.CW)
        path.addCircle(x, y, 5f, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }

    private fun drawMetalPost(x: Float, y: Float) {
        var r = 0.5f
        while (r <= 5f) {
            path.addCircle(x, y, r, Path.Direction.CW)
            r += 0.5f
        }

        canvas.drawPath(path, paint)
    }

    private fun drawTrafficLight(x: Float, y: Float) {
        path.moveTo(x, y)
        path.lineTo(x, y - 18)
        path.moveTo(x - 4, y)
        path.lineTo(x + 4, y)

        rectF.set(x - 5, y - 28, x + 5, y - 18)
        path.addArc(rectF, 0f, 180f)

        path.moveTo(x - 5, y - 23)
        path.lineTo(x - 5, y - 30)
        path.moveTo(x + 5, y - 23)
        path.lineTo(x + 5, y - 30)

        rectF.set(x - 5, y - 35, x + 5, y - 25)
        path.addArc(rectF, 180f, 180f)

        path.addCircle(x, y - 22.25f, 1f, Path.Direction.CW)
        path.addCircle(x, y - 26.5f, 1f, Path.Direction.CW)
        path.addCircle(x, y - 30.75f, 1f, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawWell(x: Float, y: Float) {
        path.addCircle(x, y, 7.5f, Path.Direction.CW)
        path.moveTo(x - 5.3f, y - 5.3f)
        path.lineTo(x + 5.3f, y + 5.3f)

        canvas.drawPath(path, paint)
    }

    private fun drawGrid(x: Float, y: Float) {
        path.moveTo(x - 7.5f, y - 5f)
        path.lineTo(x + 7.5f, y - 5f)
        path.lineTo(x + 7.5f, y + 5f)
        path.lineTo(x - 7.5f, y + 5f)
        path.close()
        path.moveTo(x - 2.5f, y - 5f)
        path.lineTo(x - 2.5f, y + 5f)
        path.moveTo(x + 2.5f, y - 5f)
        path.lineTo(x + 2.5f, y + 5f)

        canvas.drawPath(path, paint)
    }

    private fun drawKilometerSign(x: Float, y: Float) {
        path.moveTo(x + 8, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 25)
        path.lineTo(x + 12.5f, y - 25)
        path.lineTo(x + 12.5f, y - 35)
        path.lineTo(x - 12.5f, y - 35)
        path.lineTo(x - 12.5f, y - 25)
        path.lineTo(x, y - 25)

        canvas.drawPath(path, paint)
    }

    private fun drawBusStopSign(x: Float, y: Float) {
        path.moveTo(x + 8, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 25)
        path.lineTo(x + 25f, y - 25)
        path.lineTo(x + 25f, y - 35)
        path.lineTo(x, y - 35)
        path.lineTo(x, y - 25)
        path.lineTo(x, y - 25)

        canvas.drawPath(path, paint)
    }

    private fun drawPointerSign(x: Float, y: Float) {
        path.moveTo(x + 8, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 30)
        path.lineTo(x + 10, y - 25)
        path.moveTo(x, y - 30)
        path.lineTo(x + 10, y - 35)

        canvas.drawPath(path, paint)
    }

    private fun drawRoadSign(x: Float, y: Float) {
        path.moveTo(x + 8, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 18)
        path.lineTo(x + 5f, y - 18)
        path.lineTo(x + 5f, y - 35)
        path.lineTo(x - 5f, y - 35)
        path.lineTo(x - 5f, y - 18)
        path.lineTo(x, y - 18)

        canvas.drawPath(path, paint)
    }

    private fun drawBush(x: Float, y: Float) {
        path.addCircle(x, y, 4f, Path.Direction.CW)
        path.addCircle(x, y - 13, 3f, Path.Direction.CW)
        path.addCircle(x, y - 13, 2f, Path.Direction.CW)
        path.addCircle(x, y - 13, 1f, Path.Direction.CW)
        path.addCircle(x - 10, y + 10, 3f, Path.Direction.CW)
        path.addCircle(x - 10, y + 10, 2f, Path.Direction.CW)
        path.addCircle(x - 10, y + 10, 1f, Path.Direction.CW)
        path.addCircle(x + 10, y + 10, 3f, Path.Direction.CW)
        path.addCircle(x + 10, y + 10, 2f, Path.Direction.CW)
        path.addCircle(x + 10, y + 10, 1f, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawTree(x: Float, y: Float) {
        path.addCircle(x, y, 3f, Path.Direction.CW)

        rectF.set(x - 4, y - 32, x + 4, y - 5)
        path.moveTo(x, y - 14.5f)
        path.addOval(rectF, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawConifer(x: Float, y: Float) {
        path.moveTo(x + 5, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 35)
        path.lineTo(x + 6, y - 29)
        path.moveTo(x, y - 35)
        path.lineTo(x - 6, y - 29)
        path.moveTo(x, y - 25)
        path.lineTo(x + 8, y - 17)
        path.moveTo(x, y - 25)
        path.lineTo(x - 8, y - 17)
        path.moveTo(x, y - 15)
        path.lineTo(x + 10, y - 5)
        path.moveTo(x, y - 15)
        path.lineTo(x - 10, y - 5)

        canvas.drawPath(path, paint)
    }

    private fun drawPoint(x: Float, y: Float) {
        path.moveTo(x - 2, y - 2)
        path.lineTo(x + 2, y + 2)
        path.moveTo(x + 2, y - 2)
        path.lineTo(x - 2, y + 2)

        canvas.drawPath(path, paint)
    }

    private fun initPaintPath(point: PointObjectCoordinate) {
        paint.reset()
        path.reset()
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = point.weight
            color = point.color
        }
    }

    private fun drawText(point: PointObjectCoordinate) {
        if (point.textAttribute.isNotBlank()) {
            paint.strokeWidth = point.weight
            canvas.drawText(point.textAttribute, point.x, point.y - 20, paint)
        }
    }
}