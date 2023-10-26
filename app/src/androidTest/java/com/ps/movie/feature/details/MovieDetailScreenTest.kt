package com.ps.movie.feature.details

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ps.data.di.NetworkModule
import com.ps.domain.modal.MovieDetailResponse
import com.ps.movie.MovieActivity
import com.ps.movie.di.TestRepositoryModule
import com.ps.movie.di.TestUseCaseModule
import com.ps.movie.feature.common.DisplayTitle
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
class MovieDetailScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MovieActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun checkMovieDetailScreenIsVisible() {
        composeRule.onNodeWithTag(TestTags.MOVIE_DETAIL_TITLE).assertExists()
        composeRule.onNodeWithTag(TestTags.MOVIE_DETAIL_TITLE).assertIsDisplayed()
    }

    @Test
    fun validateMovieDetailScreenTitle() {
        val titleText = "Movie List"
        composeRule.activity.setContent {
            DisplayTitle(titleText)
        }
        composeRule.onNode(hasText(text = titleText))
    }

    @Test
    fun displayMovieDetails() {
        composeRule.activity.setContent {
            DisplayMovieDetails(
                movie = MovieDetailResponse(
                    id = 1,
                    title = "title",
                    overview = "overview",
                    posterPath = "posterPath",
                    backdropPath = "backdropPath",
                    voteAverage = 1.0,
                    voteCount = 1,
                ),
            )
        }
        composeRule.onNodeWithTag(TestTags.MOVIE_DETAIL).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.MOVIE_DETAIL_OVERVIEW).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.MOVIE_DETAIL_VOTE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.MOVIE_DETAIL_VOTE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.MOVIE_DETAIL_RATING).assertIsDisplayed()
    }
}
