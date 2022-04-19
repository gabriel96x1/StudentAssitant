package com.rzs.studentassistant.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimerStructure(
    val title: String,
    val minutes: Int,
    val seconds: Int,
    @PrimaryKey val id: Int? = null
)
