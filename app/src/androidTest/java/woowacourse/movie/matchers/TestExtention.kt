package woowacourse.movie.matchers

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers

fun ViewInteraction.performClick(): ViewInteraction = this.perform(click())

fun ViewInteraction.isDisplayed() = check(matches(ViewMatchers.isDisplayed()))
