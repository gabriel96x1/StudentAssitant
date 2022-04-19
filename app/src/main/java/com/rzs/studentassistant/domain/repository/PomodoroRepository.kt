package com.rzs.studentassistant.domain.repository

import com.rzs.studentassistant.domain.model.TimerStructure

interface PomodoroRepository {

    suspend fun getTimerTime(timeType : String): TimerStructure?

    suspend fun insertNewTime(timerStructure: TimerStructure)

    suspend fun nukeTimes()

}