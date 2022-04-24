package com.hammak.dbproject.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Class::class,
            parentColumns = ["id"],
            childColumns = ["class_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Hull::class,
            parentColumns = ["id"],
            childColumns = ["hull_id"],
            onDelete = CASCADE
        )],
    indices = [Index(
        value = ["name"],
        unique = true
    ), Index(value = ["class_id"]), Index(value = ["hull_id"])]
)
data class Ship(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "class_id") val classId: Int,
    @ColumnInfo(name = "hull_id") val hullId: Int
)