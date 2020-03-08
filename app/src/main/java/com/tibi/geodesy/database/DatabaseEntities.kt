package com.tibi.geodesy.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity()
data class Project constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val instrument: String
)

@Entity
data class Measurement constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val va: Double,
    val ha: Double,
    val sd: Double,
    val ht: Double,
    @ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"])
    val projectId: Long,
    @ForeignKey(entity = Station::class, parentColumns = ["id"], childColumns = ["StationId"])
    val StationId: Long,
    @ForeignKey(entity = Station::class, parentColumns = ["id"], childColumns = ["backsightId"])
    val backsightId: Long
)

@Entity
data class Station constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val hi: Double,
    val x: Double,
    val y: Double,
    val z: Double
)

@Entity
data class Coordinate constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ForeignKey(entity = Measurement::class, parentColumns = ["id"], childColumns = ["measurementId"])
    val measurementId: Long,
    val x: Double,
    val y: Double,
    val z: Double
)

@Entity
data class PointObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])
    val coordinateId: Long,
    val angle: Double,
    val textAttribute: String,
    @ForeignKey(entity = DrawObject::class, parentColumns = ["id"], childColumns = ["objectId"])
    val objectId: Long
)

@Entity
data class LinearObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])
    val coordinateId: Long,
    @ForeignKey(entity = DrawObject::class, parentColumns = ["id"], childColumns = ["objectId"])
    val objectId: Long,
    val pointIndex: Int
)

@Entity
data class DrawObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val type: String,
    val color: Int,
    val layer: String,
    val lineType: String,
    val lineWeight: Double
)



