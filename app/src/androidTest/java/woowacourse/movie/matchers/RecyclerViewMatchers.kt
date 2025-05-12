package woowacourse.movie.matchers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import woowacourse.movie.R

object RecyclerViewMatchers {
    /**
     * RecyclerView의 특정 위치에 있는 아이템 뷰 내부의 특정 뷰(View)를 매칭하기 위한 Matcher
     *
     * Matcher는 RecyclerView의 지정된 position에 해당하는 ViewHolder를 탐색,
     * 그 ViewHolder 안에서 주어진 뷰 ID를 가진 뷰가 현재 검증 대상 뷰(view)와 일치하는지를 판단
     *
     * @param position RecyclerView 내에서 확인하고자 하는 아이템의 위치 (0부터 시작)
     * @param targetViewId 해당 아이템 뷰 내에서 검증하고자 하는 자식 뷰의 ID
     * @return Espresso에서 사용할 수 있는 View Matcher
     */
    fun atPositionOnView(
        position: Int,
        targetViewId: Int,
    ) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) = Unit

        override fun matchesSafely(view: View): Boolean {
            val recyclerView =
                view.rootView.findViewById<RecyclerView>(R.id.rv)
                    ?: return false
            val viewHolder =
                recyclerView.findViewHolderForAdapterPosition(position)
                    ?: return false
            val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
            return view === targetView
        }
    }
}
