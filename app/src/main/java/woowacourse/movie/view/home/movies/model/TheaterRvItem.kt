package woowacourse.movie.view.home.movies.model

import androidx.annotation.LayoutRes
import woowacourse.movie.R

sealed class TheaterRvItem(val viewType: ViewType) {
    data class TheaterItem(
        val name: String,
        val movieId: Int,
        val bookingAbleTimeCount: Int,
    ) : TheaterRvItem(ViewType.VIEW_TYPE_THEATER)

    enum class ViewType(
        @LayoutRes val layoutRes: Int,
    ) {
        VIEW_TYPE_THEATER(R.layout.theater_item),
    }
}
