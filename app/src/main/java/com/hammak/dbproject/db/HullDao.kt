package com.hammak.dbproject.db

import androidx.room.*

@Dao
interface HullDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHulls(vararg hulls: Hull): List<Long>

    @Delete
    fun deleteHulls(vararg hulls: Hull)

    @Query("DELETE FROM hull")
    fun deleteAll()

    @Query("SELECT * FROM hull WHERE id=:id")
    fun getHull(id: Int): Hull

    @Query("SELECT * FROM hull")
    fun getAllHulls(): Array<Hull>
}