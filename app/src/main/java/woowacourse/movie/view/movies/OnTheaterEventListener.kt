package woowacourse.movie.view.movies

import woowacourse.movie.domain.Theater

interface OnTheaterEventListener {
    fun onClickReservation(theater: Theater)
}
