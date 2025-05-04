package woowacourse.movie.presentation.view.home.movies.adapter.item

import woowacourse.movie.presentation.model.MovieUiModel

data class MovieItem(
    val movie: MovieUiModel,
) : MovieMainItem(MovieViewType.MOVIE) {
    override val id: Long = movie.id.toLong()
}
