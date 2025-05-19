package woowacourse.movie.matchers

import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun isChecked(): Matcher<View> {
    return object : BoundedMatcher<View, SwitchCompat>(SwitchCompat::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("is checked")
        }

        override fun matchesSafely(item: SwitchCompat): Boolean {
            return item.isChecked
        }
    }
}
