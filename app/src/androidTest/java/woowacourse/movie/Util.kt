package woowacourse.movie

import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun nthChildOf(
    parentMatcher: Matcher<View>,
    childPosition: Int,
): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) = Unit

        override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup &&
                parentMatcher.matches(parent) &&
                parent.getChildAt(childPosition) == view
        }
    }
}
