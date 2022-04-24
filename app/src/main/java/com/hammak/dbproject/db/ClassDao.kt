package com.hammak.dbproject.db

import androidx.room.*

@Dao
interface ClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClasses(vararg classes: Class): List<Long>

    @Delete
    fun deleteClasses(vararg classes: Class)

    @Query("DELETE FROM class")
    fun deleteAll()

    @Query("SELECT * FROM class WHERE id=:id")
    fun getClass(id: Int): Class

    @Query("SELECT * FROM class")
    fun getAllClasses(): List<Class>
}