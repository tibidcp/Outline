package com.tibi.geodesy.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Project constructor(
    @PrimaryKey
    val name: String,
    val instrument: String = "Nikon"
)