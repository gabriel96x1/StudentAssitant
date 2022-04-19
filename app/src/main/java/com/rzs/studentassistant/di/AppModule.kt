package com.rzs.studentassistant.di

import android.app.Application
import androidx.room.Room
import com.rzs.studentassistant.data.data_source.PomodoroDatabase
import com.rzs.studentassistant.data.repository.PomodoroRepositoryImpl
import com.rzs.studentassistant.domain.repository.PomodoroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePomodoroDatabase(app: Application): PomodoroDatabase {
        return Room.databaseBuilder(
            app,
            PomodoroDatabase::class.java,
            PomodoroDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePomodoroRepository(db: PomodoroDatabase): PomodoroRepository {
        return PomodoroRepositoryImpl(db.pomodoroConfigDao)
    }

}