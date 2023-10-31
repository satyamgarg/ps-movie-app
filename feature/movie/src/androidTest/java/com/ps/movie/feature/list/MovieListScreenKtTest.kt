package com.ps.movie.feature.list

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.ps.data.di.NetworkModule
import com.ps.domain.modal.MovieResult
import com.ps.movie.di.TestRepositoryModule
import com.ps.movie.di.TestUseCaseModule
import com.ps.movie.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(
    NetworkModule::class,
    TestRepositoryModule::class,
    TestUseCaseModule::class,
)
class MovieListScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<com.ps.movie.MovieActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun checkMovieListScreenIsVisible() {
        composeRule.onNodeWithTag(TestTags.MOVIE_LIST_TITLE).assertIsDisplayed()
        composeRule.onAllNodesWithText(TestTags.MOVIE_LIST_ITEM_IMAGE)
        composeRule.waitForIdle()
    }

    @Test
    fun movieListScreenIsHeaderTitleCorrect() {
        val titleText = "Movie List"
        composeRule.activity.setContent {
            MovieListScreen {}
        }
        composeRule.onNode(hasText(text = titleText))
    }

    @Test
    fun onAppLaunchIfMovieListEmpty() {
        composeRule.activity.setContent {
            DisplayMovieList(results = emptyList(), onMovieClick = {})
        }

        composeRule.onNodeWithTag(testTag = TestTags.MOVIE_LIST)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun onAppLaunchIfMovieFirstListItemPresent() {
        val movieList = listOf(
            MovieResult(id = 10, title = "EXPANDABLE", posterPath = "/poster"),
            MovieResult(id = 10, title = "MISSION-IMPOSSIBLE", posterPath = "/poster1"),
        )
        composeRule.activity.setContent {
            DisplayMovieList(results = movieList, onMovieClick = {})
        }

        composeRule.onNodeWithTag(testTag = TestTags.MOVIE_LIST)
            .onChildren()
            .assertCountEquals(2)
    }
}
