@file:OptIn(ExperimentalComposeUiApi::class)
package com.rzs.studentassitant.ui.views

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.rzs.studentassitant.model.MainMenuSectionModel
import com.rzs.studentassitant.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

internal val tabList = listOf("Pomodoro", "Homework")


@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navigateToTutorial: (String) -> Unit,

    ) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {

        val context = LocalContext.current

        val state =
            rememberSearchState(
                initialResults = viewModel.tutorialList,
                suggestions = suggestionList,
                timeoutMillis = 600,
            ) { query: TextFieldValue ->
                viewModel.getTutorials(query.text)
            }

        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        val dispatcher: OnBackPressedDispatcher =
            LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!state.focused) {
                        isEnabled = false
                        Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
                        dispatcher.onBackPressed()
                    } else {
                        state.query = TextFieldValue("")
                        state.focused = false
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                }
            }
        }

        DisposableEffect(dispatcher) { // dispose/relaunch if dispatcher changes
            dispatcher.addCallback(backCallback)
            onDispose {
                backCallback.remove() // avoid leaks!
            }
        }


    }
}


@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeContent(
    modifier: Modifier,
    tutorialList: List<List<MainMenuSectionModel>>,
    navigateToTutorial: (String) -> Unit
) {

    val pagerState: PagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        edgePadding = 8.dp,
        // selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        // Add tabs for all of our pages
        tabList.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        count = tabList.size
    ) { page: Int ->

        when (page) {
            0 -> TutorialListContent(modifier, tutorialList[0], navigateToTutorial)
            1 -> TutorialListContent(modifier, tutorialList[1], navigateToTutorial)
            2 -> TutorialListContent(modifier, tutorialList[2], navigateToTutorial)
            3 -> TutorialListContent(modifier, tutorialList[3], navigateToTutorial)
            4 -> TutorialListContent(modifier, tutorialList[4], navigateToTutorial)
            else -> ComingSoonScreen()
        }
    }
}

@Composable
fun ComingSoonScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "⚠️ Under Construction!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
    }
}

@ExperimentalAnimationApi
@Composable
fun TutorialListContent(
    modifier: Modifier = Modifier,
    sectionList: List<MainMenuSectionModel>,
    navigateToTutorial: (String) -> Unit
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xffEEEEEE)
    ) {

        Box {

            val scrollState = rememberLazyListState()

            // List of Tutorials
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {

                    items(tutorialList) { item: MainMenuSectionModel ->

                        var isExpanded by remember(key1 = item.title) { mutableStateOf(item.expanded) }

                        TutorialSectionCard(
                            model = item,
                            onClick = {
                                navigateToTutorial(item.title)
                            },
                            onExpandClicked = {
                                item.expanded = !item.expanded
                                isExpanded = item.expanded
                            },
                            expanded = isExpanded
                        )
                    }
                }
            )

            // Jump to bottom button shows up when user scrolls past a threshold.
            // Convert to pixels:
            val jumpThreshold = with(LocalDensity.current) {
                56.dp.toPx()
            }

            // Show the button if the first visible item is not the first one or if the offset is
            // greater than the threshold.
            val jumpToBottomButtonEnabled by remember {
                derivedStateOf {
                    scrollState.firstVisibleItemIndex != 0 ||
                            scrollState.firstVisibleItemScrollOffset > jumpThreshold
                }
            }

            val coroutineScope = rememberCoroutineScope()
            JumpToBottom(
                enabled = jumpToBottomButtonEnabled,
                onClicked = {
                    coroutineScope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}