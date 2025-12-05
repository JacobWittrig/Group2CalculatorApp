package com.example.jacobapplication2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAddition() {
        onView(withId(R.id.button_1)).perform(click())
        onView(withId(R.id.button_add)).perform(click())
        onView(withId(R.id.button_2)).perform(click())
        onView(withId(R.id.button_equals)).perform(click())

        onView(withId(R.id.calculator_display)).check(matches(withText("3.0")))
    }
}