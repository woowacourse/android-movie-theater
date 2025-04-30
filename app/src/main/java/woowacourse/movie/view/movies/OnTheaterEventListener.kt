package woowacourse.movie.view.movies

import woowacourse.movie.domain.Showings

interface OnTheaterEventListener {
    fun onClickReservation(showings: Showings)
}
