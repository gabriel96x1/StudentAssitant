package com.rzs.studentassistant.data.data_source

import androidx.room.*
import com.rzs.studentassistant.domain.model.TimerStructure
import kotlinx.coroutines.flow.Flow

@Dao
interface PomodoroConfigDao {

    @Query("SELECT * FROM timerstructure WHERE title = :timeType")
    suspend fun getTimerTime(timeType : String): TimerStructure?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTime(timerStructure: TimerStructure)

    @Query("DELETE FROM timerstructure")
    suspend fun nukeTimes()
}