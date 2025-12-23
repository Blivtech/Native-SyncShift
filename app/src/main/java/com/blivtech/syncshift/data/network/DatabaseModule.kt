package com.blivtech.syncshift.data.network

import android.content.Context
import androidx.room.Room
import com.blivtech.syncshift.data.model.local.AppDatabase
import com.blivtech.syncshift.data.model.local.Dao.AttendanceDao
import com.blivtech.syncshift.data.model.local.Dao.EmployeeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "syncshift_db"   // single DB name
        ).build()
    }

    @Provides
    fun provideEmployeeDao(db: AppDatabase): EmployeeDao {
        return db.employeeDao()
    }

    @Provides
    fun provideAttendanceDao(
        database: AppDatabase
    ): AttendanceDao = database.attendanceDao()
}
