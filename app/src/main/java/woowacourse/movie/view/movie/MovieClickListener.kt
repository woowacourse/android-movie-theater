package woowacourse.movie.view.movie

import woowacourse.movie.view.item.Movie

interface MovieClickListener {
    fun onReservationClick(movie: Movie)
}
