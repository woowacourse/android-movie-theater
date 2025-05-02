package woowacourse.movie.view.home.movies.model

import androidx.annotation.LayoutRes
import woowacourse.movie.R
import woowacourse.movie.view.bindingadapter.ImageSource

sealed class MovieRvItem(val viewType: ViewType) {
    data class MovieItem(
        val id: Int,
        val title: String,
        val imgName: ImageSource,
        val releaseStartDate: String,
        val releaseEndDate: String,
        val runningTime: Int,
    ) : MovieRvItem(ViewType.VIEW_TYPE_MOVIE)

    data class AdItem(
        val imgResource: ImageSource,
    ) : MovieRvItem(ViewType.VIEW_TYPE_ADVERTISEMENT)

    enum class ViewType(
        @LayoutRes val layoutRes: Int,
    ) {
        VIEW_TYPE_ADVERTISEMENT(R.layout.advertisement_item),
        VIEW_TYPE_MOVIE(R.layout.movie_item),
    }
}
