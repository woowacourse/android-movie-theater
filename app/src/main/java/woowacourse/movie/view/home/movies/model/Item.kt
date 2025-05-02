package woowacourse.movie.view.home.movies.model

import androidx.annotation.LayoutRes
import woowacourse.movie.R

sealed class Item(val viewType: ViewType) {
    data class MovieItem(
        val id: Int,
        val title: String,
        val imgName: String,
        val releaseStartDate: String,
        val releaseEndDate: String,
        val runningTime: Int,
    ) : Item(ViewType.VIEW_TYPE_MOVIE)

    data class AdvertiseItem(
        val imgResource: String,
    ) : Item(ViewType.VIEW_TYPE_ADVERTISEMENT)

    enum class ViewType(
        @LayoutRes val layoutRes: Int,
    ) {
        VIEW_TYPE_ADVERTISEMENT(R.layout.advertisement_item),
        VIEW_TYPE_MOVIE(R.layout.movie_item),
    }
}
