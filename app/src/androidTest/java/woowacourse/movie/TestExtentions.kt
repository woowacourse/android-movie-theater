package woowacourse.movie

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

fun ViewInteraction.performClick(): ViewInteraction = this.perform(click())

fun ViewInteraction.checkIsDisplayed(): ViewInteraction = this.check(matches(isDisplayed()))

fun ViewInteraction.checkWithText(text: String): ViewInteraction = this.check(matches(withText(text)))
