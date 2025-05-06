package woowacourse.movie.view.movie

import woowacourse.movie.model.Movie

fun interface MovieClickListener {
    fun onReservationClick(movie: Movie)
}
