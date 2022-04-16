package com.rzs.studentassitant.model

import androidx.compose.runtime.Composable

data class MainMenuSectionModel(
    val title: String,
    val action: @Composable (() -> Unit)? = null,
    val description: String
    )
