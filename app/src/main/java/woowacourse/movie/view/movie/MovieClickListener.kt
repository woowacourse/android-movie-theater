package woowacourse.movie.view.movie

import woowacourse.movie.domain.model.Movie

interface MovieClickListener {
    fun onReservationClick(movie: Movie)
}
