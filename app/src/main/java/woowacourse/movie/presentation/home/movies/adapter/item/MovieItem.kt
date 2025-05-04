package woowacourse.movie.presentation.home.movies.adapter.item

import woowacourse.movie.presentation.common.model.MovieUiModel

data class MovieItem(
    val movie: MovieUiModel,
) : MovieMainItem(MovieViewType.MOVIE) {
    override val id: Long = movie.id.toLong()
}
