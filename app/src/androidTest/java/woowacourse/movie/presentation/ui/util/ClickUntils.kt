package woowacourse.movie.presentation.ui.util

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions

fun ViewInteraction.repeatClick(count: Int) {
    repeat(count) {
        perform(ViewActions.click())
    }
}
