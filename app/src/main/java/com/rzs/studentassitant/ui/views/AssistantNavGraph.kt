package com.rzs.studentassitant.ui.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rzs.studentassitant.model.MainMenuSectionModel
import com.rzs.studentassitant.ui.viewmodels.MainViewModel

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AssistantNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.POMODORO_START,
) {
    val mainViewModel: MainViewModel = viewModel()

    val layoutPomodoro: List<MainMenuSectionModel> = createPomodoroLayout {
        navController.navigateUp()
    }

    val layoutHomework = createHomeworkLayout()

    mainViewModel.sectionList.add(componentTutorialList)
    mainViewModel.sectionList.add(layoutTutorialList)
}

object Destinations {
    const val POMODORO_START = "start_destinations"
}