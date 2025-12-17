package com.blivtech.syncshift.data.network

import android.content.Context
import androidx.room.Room
import com.blivtech.syncshift.data.model.local.RoomDatabase
import com.blivtech.syncshift.data.model.local.EmployeeDao
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
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "syncshift_db"   // single DB name
        ).build()
    }

    @Provides
    fun provideEmployeeDao(db: RoomDatabase): EmployeeDao {
        return db.employeeDao()
    }
}
