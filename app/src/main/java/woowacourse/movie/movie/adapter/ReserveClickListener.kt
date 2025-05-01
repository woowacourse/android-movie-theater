package woowacourse.movie.movie.adapter

import woowacourse.movie.ui.model.MovieUiModel

fun interface ReserveClickListener {
    fun onReserveClick(movie: MovieUiModel)
}
