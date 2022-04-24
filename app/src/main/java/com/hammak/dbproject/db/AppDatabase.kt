package com.hammak.dbproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Ship::class, Class::class, Hull::class, Cargo::class, HullCargoSupport::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shipDao(): ShipDao
    abstract fun classDao(): ClassDao
    abstract fun hullDao(): HullDao
    abstract fun cargoDao(): CargoDao
    abstract fun hullCargoSupportDao(): HullCargoSupportDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database_name"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}