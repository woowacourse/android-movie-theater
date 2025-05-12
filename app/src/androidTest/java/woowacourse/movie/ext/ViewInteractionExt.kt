package woowacourse.movie.ext

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
fun ViewInteraction.isDisplayed(): ViewInteraction = check(matches(ViewMatchers.isDisplayed()))
