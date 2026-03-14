package com.blivtech.syncshift.data.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blivtech.syncshift.data.model.local.Dao.AttendanceDao
import com.blivtech.syncshift.data.model.local.Dao.EmployeeDao
import com.blivtech.syncshift.data.model.local.Entity.AttendanceEntity
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.ui.company.CompanyDao
import com.blivtech.syncshift.ui.company.CompanyEntity
import com.blivtech.syncshift.ui.company.ShiftEntity
import com.blivtech.syncshift.ui.home.fragment.home.ShiftAttendanceEntity
import com.blivtech.syncshift.ui.home.fragment.home.ShiftDao

@Database(
    entities = [
        EmployeeEntity::class,
        AttendanceEntity::class, CompanyEntity::class,ShiftEntity::class,ShiftAttendanceEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(AttendanceStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun companyDao(): CompanyDao
    abstract fun shiftDao(): ShiftDao
}
