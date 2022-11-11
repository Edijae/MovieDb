package com.samuel.movie

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.samuel.movie.adapters.MoviesAdapter
import com.samuel.movie.ui.activities.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private var appIdlingResource: IdlingResource? = null

    /**
     * Use [to launch and get access to the activity.][ActivityScenario.onActivity]
     */
    @Before
    fun registerIdlingResource() {
        activityRule.scenario.onActivity { activity ->
            appIdlingResource = activity.getIdlingResource()
            IdlingRegistry.getInstance().register(appIdlingResource)
        }
    }

    @Test
    fun uiTest() {
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(3, click())
        )

        onView(withId(R.id.descTv)).check(matches(isDisplayed()))
    }

    @After
    fun unregisterIdlingResource() {
        if (appIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(appIdlingResource)
        }
    }
}