package com.rzs.studentassistant.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rzs.studentassistant.domain.model.TimerStructure

@Database(
    entities = [TimerStructure::class],
    version = 1
)
abstract class PomodoroDatabase: RoomDatabase() {

    abstract val pomodoroConfigDao: PomodoroConfigDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}