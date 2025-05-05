package woowacourse.movie.matchers

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {
    fun atPositionOnView(
        position: Int,
        targetViewId: Int,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null

            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                this.resources?.let {
                    idDescription =
                        runCatching {
                            it.getResourceName(recyclerViewId)
                        }.onFailure { e ->
                            String.format("%s (resource name not found)", recyclerViewId)
                        }.getOrThrow()
                }
                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {
                this.resources = view.resources
                val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
                val childView = recyclerView?.findViewHolderForAdapterPosition(position)?.itemView

                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView?.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }

    companion object {
        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }
    }
}

fun scrollToPosition(position: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "Scroll RecyclerView to position $position"
        }

        override fun getConstraints(): Matcher<View> {
            return Matchers.allOf(
                isAssignableFrom(RecyclerView::class.java),
                ViewMatchers.isDisplayed(),
            )
        }

        override fun perform(
            uiController: UiController?,
            view: View?,
        ) {
            if (view is RecyclerView) {
                val layoutManager = view.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    layoutManager.scrollToPositionWithOffset(position, 0)
                    uiController?.loopMainThreadUntilIdle()
                } else {
                    throw IllegalStateException(
                        "RecyclerView's LayoutManager is not LinearLayoutManager. Cannot use scrollToPositionWithOffset.",
                    )
                }
            } else {
                throw IllegalStateException("View is not a RecyclerView")
            }
        }
    }
}
