package com.tibi.geodesy.utils

import android.util.Log
import com.tibi.geodesy.database.LinearObjectCoordinate
import com.tibi.geodesy.database.PointObjectCoordinate
import com.tibi.geodesy.database.PointType

fun selectCanvasObject(
    maxX: Float,
    maxY: Float,
    minX: Float,
    minY: Float,
    points: List<PointObjectCoordinate>,
    lines: List<LinearObjectCoordinate>
) : Any? {
    val selectedPoints = points.filter { point ->
        point.x in minX..maxX && point.y in minY..maxY
    }

    val selectedLines = lines.groupBy { it.linearObjectId }
        .values
        .filter { pts ->
            pts.sortedBy { it.pointIndex }
                .zipWithNext()
                .any { pair ->
                    intersection(Point2d(minX, minY), Point2d(maxX, maxY),
                    Point2d(pair.first.x, pair.first.y), Point2d(pair.second.x, pair.second.y))
                            || intersection(Point2d(minX, maxY), Point2d(maxX, minY),
                                Point2d(pair.first.x, pair.first.y), Point2d(pair.second.x, pair.second.y))
                }
        }
    if (selectedLines.isNotEmpty()) {
        return selectedLines.first().first()
    }
    if (selectedPoints.isNotEmpty()) {
        return selectedPoints.firstOrNull { it.type != PointType.POINT.name } ?: selectedPoints.first()
    }
    return null
}

data class Point2d(val x: Float, val y: Float)

private fun intersection(start1: Point2d, end1: Point2d, start2: Point2d, end2: Point2d): Boolean {

    val dir1 = Point2d(end1.x - start1.x, end1.y - start1.y)
    val dir2 = Point2d(end2.x - start2.x, end2.y - start2.y)

    //считаем уравнения прямых проходящих через отрезки
    val a1 = - dir1.y
    val b1 = + dir1.x
    val d1 = - (a1 * start1.x + b1 * start1.y)

    val a2 = - dir2.y
    val b2 = + dir2.x
    val d2 = - (a2 * start2.x + b2 * start2.y)

    //подставляем концы отрезков, для выяснения в каких полуплоскотях они
    val seg1_line2_start = a2 * start1 . x +b2 * start1.y + d2
    val seg1_line2_end = a2 * end1 . x +b2 * end1.y + d2

    val seg2_line1_start = a1 * start2 . x +b1 * start2.y + d1
    val seg2_line1_end = a1 * end2 . x +b1 * end2.y + d1

    //если концы одного отрезка имеют один знак, значит он в одной полуплоскости и пересечения нет.
    if (seg1_line2_start * seg1_line2_end >= 0 || seg2_line1_start * seg2_line1_end >= 0) {
        return false
    }
    return true
}