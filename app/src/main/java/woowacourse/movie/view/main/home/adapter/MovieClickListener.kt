package woowacourse.movie.view.main.home.adapter

import woowacourse.movie.model.movie.Movie

fun interface MovieClickListener {
    fun onReservationClick(movie: Movie)
}
