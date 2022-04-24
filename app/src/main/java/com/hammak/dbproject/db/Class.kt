package com.hammak.dbproject.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Class(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "speed") val speed: Int,
    @ColumnInfo(name = "volume") val volume: Int
)