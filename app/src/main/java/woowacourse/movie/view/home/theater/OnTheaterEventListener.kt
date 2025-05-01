package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.Showings

interface OnTheaterEventListener {
    fun onClickReservation(showings: Showings)
}
