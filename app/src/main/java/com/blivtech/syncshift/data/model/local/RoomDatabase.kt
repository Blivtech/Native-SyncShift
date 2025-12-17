package com.blivtech.syncshift.data.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EmployeeEntity::class],
    version = 1
)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}
