package com.tibi.geodesy.draw

import android.graphics.*
import com.tibi.geodesy.database.LinearObjectCoordinate
import com.tibi.geodesy.database.LinearType
import com.tibi.geodesy.database.PointObjectCoordinate
import com.tibi.geodesy.database.PointType

class Draw (private val canvas: Canvas) {
    private val paint = Paint()
    private val path = Path()
    private val rectF = RectF()
    private val dashPathEffect = DashPathEffect(floatArrayOf(1.0f, 0.5f), 0f)
    private val shape = Path()

    fun drawAllPoint(points: List<PointObjectCoordinate>) {
        points.forEach { point ->
            val x = point.x
            val y = -point.y
            initPaintPath(point.weight, point.color)
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
        lines.groupBy { it.linearObjectId }
            .values
            .forEach { points ->
                if (points.size > 1) {
                    paint.reset()
                    path.reset()
                    initLinearPaint(points.first())
                    points.sortedBy { it.pointIndex }
                        .forEachIndexed { index, point ->
                            if (index == 0) {
                                path.moveTo(point.x, -point.y)
                            } else {
                                path.lineTo(point.x, -point.y)
                                canvas.drawPath(path, paint)
                                path.reset()
                                path.moveTo(point.x, -point.y)
                            }
                        }
                }
            }
    }

    private fun initLinearPaint(line: LinearObjectCoordinate) {
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = line.weight
            color = line.color
        }

        when (line.type) {
            LinearType.CONTINUOUS.name -> return
            LinearType.DASHED.name -> paint.pathEffect = dashPathEffect
            LinearType.SMALL_METAL_FENCE.name -> {
                shape.apply {
                    reset()
                    addRect(0f, -0.05f, 0.7f, 0.05f, Path.Direction.CW)
                    addRect(1.3f, -0.05f, 2.0f, 0.05f, Path.Direction.CW)
                    addCircle(1.0f, 0f, 0.3f, Path.Direction.CW)
                }
                paint.pathEffect = PathDashPathEffect(shape, 2.0f, 0f,
                    PathDashPathEffect.Style.ROTATE)
            }
            LinearType.BIG_METAL_FENCE.name -> {
                shape.apply {
                    reset()
                    addRect(0f, -0.05f, 0.7f, 0.05f, Path.Direction.CW)
                    addRect(1.3f, -0.05f, 2.0f, 0.05f, Path.Direction.CW)
                    addRect(0.95f, 0.3f, 1.05f, 0.5f, Path.Direction.CW)
                    addCircle(1.0f, 0f, 0.3f, Path.Direction.CW)
                }
                paint.pathEffect = PathDashPathEffect(shape, 2.0f, 0f,
                    PathDashPathEffect.Style.ROTATE)
            }
            LinearType.STONE_FENCE.name -> {
                shape.apply {
                    reset()
                    addRect(0f, -0.05f, 0.7f, 0.05f, Path.Direction.CW)
                    addRect(1.3f, -0.05f, 2.0f, 0.05f, Path.Direction.CW)
                    addRect(0.7f, -0.3f, 1.3f, 0.3f, Path.Direction.CW)
                    addRect(0.95f, 0.3f, 1.05f, 0.5f, Path.Direction.CW)
                }
                paint.pathEffect = PathDashPathEffect(shape, 2.0f, 0f,
                    PathDashPathEffect.Style.ROTATE)
            }
            LinearType.WALL_FENCE.name -> {
                shape.apply {
                    reset()
                    addRect(0f, -0.05f, 1.0f, 0.05f, Path.Direction.CW)
                    addRect(1.0f, -0.05f, 2.0f, 0.05f, Path.Direction.CW)
                    moveTo(0.8f, 0.05f)
                    lineTo(1.0f, 0.4f)
                    lineTo(1.2f, 0.05f)
                }
                paint.pathEffect = PathDashPathEffect(shape, 2.0f, 0f,
                    PathDashPathEffect.Style.ROTATE)
            }
            else -> throw java.lang.IllegalArgumentException("Wrong linear type")
        }
    }

    private fun drawStation(x: Float, y: Float) {
        path.moveTo(x, y - 1.73f)
        path.lineTo(x + 1.5f, y + 0.866f)
        path.lineTo(x - 1.5f, y + 0.866f)
        path.close()
        path.addCircle(x, y, 0.2f, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawStoneLight(x: Float, y: Float) {
        path.addCircle(x, y, 0.05f, Path.Direction.CW)
        path.addCircle(x, y, 0.5f, Path.Direction.CW)
        path.moveTo(x, y - 0.5f)
        path.lineTo(x, y - 3.0f)
        path.moveTo(x, y - 2.7f)
        path.lineTo(x + 1.5f, y - 2.7f)
        path.moveTo(x + 0.7f, y - 2.7f)
        path.lineTo(x + 0.7f, y - 1.7f)
        path.moveTo(x + 1.2f, y - 2.7f)
        path.lineTo(x + 1.2f, y - 1.7f)

        rectF.set(x + 0.7f, y - 2.0f, x + 1.2f, y - 1.4f)
        path.addArc(rectF, 0f, 180f)

        canvas.drawPath(path, paint)
    }

    private fun drawMetalLight(x: Float, y: Float) {
        var r = 0.05f
        while (r <= 0.5f) {
            path.addCircle(x, y, r, Path.Direction.CW)
            r += 0.05f
        }

        path.moveTo(x, y - 0.5f)
        path.lineTo(x, y - 3.0f)
        path.moveTo(x, y - 2.7f)
        path.lineTo(x + 1.5f, y - 2.7f)
        path.moveTo(x + 0.7f, y - 2.7f)
        path.lineTo(x + 0.7f, y - 1.7f)
        path.moveTo(x + 1.2f, y - 2.7f)
        path.lineTo(x + 1.2f, y - 1.7f)

        rectF.set(x + 0.7f, y - 2.0f, x + 1.2f, y - 1.4f)
        path.addArc(rectF, 0f, 180f)

        canvas.drawPath(path, paint)
    }

    private fun drawStonePost(x: Float, y: Float) {
        path.addCircle(x, y, 0.05f, Path.Direction.CW)
        path.addCircle(x, y, 0.5f, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }

    private fun drawMetalPost(x: Float, y: Float) {
        var r = 0.05f
        while (r <= 0.5f) {
            path.addCircle(x, y, r, Path.Direction.CW)
            r += 0.05f
        }

        canvas.drawPath(path, paint)
    }

    private fun drawTrafficLight(x: Float, y: Float) {
        path.moveTo(x, y)
        path.lineTo(x, y - 1.8f)
        path.moveTo(x - 0.4f, y)
        path.lineTo(x + 0.4f, y)

        rectF.set(x - 0.5f, y - 2.8f, x + 0.5f, y - 1.8f)
        path.addArc(rectF, 0f, 180f)

        path.moveTo(x - 0.5f, y - 2.3f)
        path.lineTo(x - 0.5f, y - 3.0f)
        path.moveTo(x + 0.5f, y - 2.3f)
        path.lineTo(x + 0.5f, y - 3.0f)

        rectF.set(x - 0.5f, y - 3.5f, x + 0.5f, y - 2.5f)
        path.addArc(rectF, 180f, 180f)

        path.addCircle(x, y - 2.225f, 0.1f, Path.Direction.CW)
        path.addCircle(x, y - 2.65f, 0.1f, Path.Direction.CW)
        path.addCircle(x, y - 3.075f, 0.1f, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawWell(x: Float, y: Float) {
        path.addCircle(x, y, 0.75f, Path.Direction.CW)
        path.moveTo(x - 0.53f, y - 0.53f)
        path.lineTo(x + 0.53f, y + 0.53f)

        canvas.drawPath(path, paint)
    }

    private fun drawGrid(x: Float, y: Float) {
        path.moveTo(x - 0.75f, y - 0.5f)
        path.lineTo(x + 0.75f, y - 0.5f)
        path.lineTo(x + 0.75f, y + 0.5f)
        path.lineTo(x - 0.75f, y + 0.5f)
        path.close()
        path.moveTo(x - 0.25f, y - 0.5f)
        path.lineTo(x - 0.25f, y + 0.5f)
        path.moveTo(x + 0.25f, y - 0.5f)
        path.lineTo(x + 0.25f, y + 0.5f)

        canvas.drawPath(path, paint)
    }

    private fun drawKilometerSign(x: Float, y: Float) {
        path.moveTo(x + 0.8f, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 2.5f)
        path.lineTo(x + 1.25f, y - 2.5f)
        path.lineTo(x + 1.25f, y - 3.5f)
        path.lineTo(x - 1.25f, y - 3.5f)
        path.lineTo(x - 1.25f, y - 2.5f)
        path.lineTo(x, y - 2.5f)

        canvas.drawPath(path, paint)
    }

    private fun drawBusStopSign(x: Float, y: Float) {
        path.moveTo(x + 0.8f, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 2.5f)
        path.lineTo(x + 2.5f, y - 2.5f)
        path.lineTo(x + 2.5f, y - 3.5f)
        path.lineTo(x, y - 3.5f)
        path.lineTo(x, y - 2.5f)
        path.lineTo(x, y - 2.5f)

        canvas.drawPath(path, paint)
    }

    private fun drawPointerSign(x: Float, y: Float) {
        path.moveTo(x + 0.8f, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 3.0f)
        path.lineTo(x + 1.0f, y - 2.5f)
        path.moveTo(x, y - 3.0f)
        path.lineTo(x + 1.0f, y - 3.5f)

        canvas.drawPath(path, paint)
    }

    private fun drawRoadSign(x: Float, y: Float) {
        path.moveTo(x + 0.8f, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 1.8f)
        path.lineTo(x + 0.5f, y - 1.8f)
        path.lineTo(x + 0.5f, y - 3.5f)
        path.lineTo(x - 0.5f, y - 3.5f)
        path.lineTo(x - 0.5f, y - 1.8f)
        path.lineTo(x, y - 1.8f)

        canvas.drawPath(path, paint)
    }

    private fun drawBush(x: Float, y: Float) {
        path.addCircle(x, y, 0.4f, Path.Direction.CW)
        path.addCircle(x, y - 1.3f, 0.3f, Path.Direction.CW)
        path.addCircle(x, y - 1.3f, 0.2f, Path.Direction.CW)
        path.addCircle(x, y - 1.3f, 0.1f, Path.Direction.CW)
        path.addCircle(x - 1.0f, y + 1.0f, 0.3f, Path.Direction.CW)
        path.addCircle(x - 1.0f, y + 1.0f, 0.2f, Path.Direction.CW)
        path.addCircle(x - 1.0f, y + 1.0f, 0.1f, Path.Direction.CW)
        path.addCircle(x + 1.0f, y + 1.0f, 0.3f, Path.Direction.CW)
        path.addCircle(x + 1.0f, y + 1.0f, 0.2f, Path.Direction.CW)
        path.addCircle(x + 1.0f, y + 1.0f, 0.1f, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawTree(x: Float, y: Float) {
        path.addCircle(x, y, 0.3f, Path.Direction.CW)

        rectF.set(x - 0.4f, y - 3.2f, x + 0.4f, y - 0.5f)
        path.moveTo(x, y - 1.45f)
        path.addOval(rectF, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    private fun drawConifer(x: Float, y: Float) {
        path.moveTo(x + 0.5f, y)
        path.lineTo(x, y)
        path.lineTo(x, y - 3.5f)
        path.lineTo(x + 0.6f, y - 2.9f)
        path.moveTo(x, y - 3.5f)
        path.lineTo(x - 0.6f, y - 2.9f)
        path.moveTo(x, y - 2.5f)
        path.lineTo(x + 0.8f, y - 1.7f)
        path.moveTo(x, y - 2.5f)
        path.lineTo(x - 0.8f, y - 1.7f)
        path.moveTo(x, y - 1.5f)
        path.lineTo(x + 1.0f, y - 0.5f)
        path.moveTo(x, y - 1.5f)
        path.lineTo(x - 1.0f, y - 0.5f)

        canvas.drawPath(path, paint)
    }

    private fun drawPoint(x: Float, y: Float) {
        path.moveTo(x - 0.2f, y - 0.2f)
        path.lineTo(x + 0.2f, y + 0.2f)
        path.moveTo(x + 0.2f, y - 0.2f)
        path.lineTo(x - 0.2f, y + 0.2f)

        canvas.drawPath(path, paint)
    }

    private fun initPaintPath(weight: Float, color: Int) {
        paint.reset()
        path.reset()
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = weight
            paint.color = color
        }
    }

    private fun drawText(point: PointObjectCoordinate) {
        if (point.textAttribute.isNotBlank()) {
            paint.strokeWidth = point.weight
            canvas.drawText(point.textAttribute, point.x, -point.y - 2.0f, paint)
        }
    }
}