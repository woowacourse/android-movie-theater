package woowacourse.movie.feature.home.view.adapter

import woowacourse.movie.feature.model.AdvertisementUiModel
import woowacourse.movie.feature.model.ContentUiModel
import woowacourse.movie.feature.model.MovieUiModel

sealed class MovieItem(
    movieItemViewType: MovieItemViewType,
) {
    val viewType: MovieItemViewType = movieItemViewType
    abstract val id: Int

    data class Movie(
        override val id: Int,
        val value: MovieUiModel,
    ) : MovieItem(MovieItemViewType.MOVIE)

    data class Advertisement(
        override val id: Int,
    ) : MovieItem(MovieItemViewType.ADVERTISEMENT)

    companion object {
        fun from(content: ContentUiModel): MovieItem =
            when (content) {
                is MovieUiModel -> Movie(0, content)
                is AdvertisementUiModel -> Advertisement(0)
            }
    }
}
