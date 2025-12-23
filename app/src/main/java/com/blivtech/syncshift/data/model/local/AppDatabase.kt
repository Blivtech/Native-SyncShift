package com.blivtech.syncshift.data.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blivtech.syncshift.data.model.local.Dao.AttendanceDao
import com.blivtech.syncshift.data.model.local.Dao.EmployeeDao
import com.blivtech.syncshift.data.model.local.Entity.AttendanceEntity
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity

@Database(
    entities = [
        EmployeeEntity::class,
        AttendanceEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(AttendanceStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao
    abstract fun attendanceDao(): AttendanceDao
}
