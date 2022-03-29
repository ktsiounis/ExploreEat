package com.example.exploreat

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.exploreat.adapter.PlacesRVAdapter
import com.example.exploreat.view.MainActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun blackMarkerIsVisibleWhenAppOpens() {
        onView(withId(R.id.user_marker_image_view)).check(matches(isDisplayed()))
    }

    @Test
    fun loadingIsVisibleWhenMapIsMoved() {
        onView(withId(R.id.map)).perform(click(), swipeRight())
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun whenMapIsMovedResultsAreVisible() {
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.map)).perform(click(), swipeRight())
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.places_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenClickOnAResult_navigationModeEnabled() {
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.map)).perform(click(), swipeRight())
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.places_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.places_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PlacesRVAdapter.PlaceHolder>(0, click())
        )
        onView(withId(R.id.selected_place_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenNavigationModeEnabled_blackMarkerIsNotVisible() {
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.map)).perform(click(), swipeRight())
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.places_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PlacesRVAdapter.PlaceHolder>(0, click())
        )
        onView(withId(R.id.user_marker_image_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenOnNavigationMode_backButtonIsClicked_explorationModeEnabled() {
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.map)).perform(click(), swipeRight())
        onView(isRoot()).perform(waitFor(4000))
        onView(withId(R.id.places_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PlacesRVAdapter.PlaceHolder>(0, click())
        )
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.selected_place_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.user_marker_image_view)).check(matches(isDisplayed()))
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

}