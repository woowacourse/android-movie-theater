package woowacourse.movie.feature.home.view.adapter

import woowacourse.movie.feature.model.AdvertisementUiModel
import woowacourse.movie.feature.model.ContentUiModel
import woowacourse.movie.feature.model.MovieUiModel

sealed class MovieItem(
    movieItemViewType: MovieItemViewType,
) {
    val viewType: MovieItemViewType = movieItemViewType
    abstract val id: Long

    data class Movie(
        override val id: Long,
        val value: MovieUiModel,
    ) : MovieItem(MovieItemViewType.MOVIE)

    data class Advertisement(
        override val id: Long,
        val value: AdvertisementUiModel,
    ) : MovieItem(MovieItemViewType.ADVERTISEMENT)

    companion object {
        fun from(content: ContentUiModel): MovieItem =
            when (content) {
                is MovieUiModel -> Movie(content.id, content)
                is AdvertisementUiModel -> Advertisement(content.id, content)
            }
    }
}
