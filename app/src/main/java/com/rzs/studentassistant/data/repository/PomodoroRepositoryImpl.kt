package com.rzs.studentassistant.data.repository

import com.rzs.studentassistant.data.data_source.PomodoroConfigDao
import com.rzs.studentassistant.domain.model.TimerStructure
import com.rzs.studentassistant.domain.repository.PomodoroRepository

class PomodoroRepositoryImpl(
    private val dao: PomodoroConfigDao
) : PomodoroRepository{
    override suspend fun getTimerTime(timeType: String): TimerStructure? {
        return dao.getTimerTime(timeType)
    }

    override suspend fun insertNewTime(timerStructure: TimerStructure) {
        return dao.insertNewTime(timerStructure)
    }

    override suspend fun nukeTimes() {
        dao.nukeTimes()
    }

}