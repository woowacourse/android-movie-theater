package woowacourse.movie.ui.movielist.view

import woowacourse.movie.domain.model.movie.Movie

fun interface BookingButtonClickListener {
    fun onClick(movie: Movie)
}
