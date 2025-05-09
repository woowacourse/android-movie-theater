package woowacourse.movie.view.movie

import woowacourse.movie.model.movie.Movie

fun interface MovieClickListener {
    fun onReservationClick(movie: Movie)
}
