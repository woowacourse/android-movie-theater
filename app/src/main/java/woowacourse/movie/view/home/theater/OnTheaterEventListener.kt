package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.Showings

fun interface OnTheaterEventListener {
    fun onClickReservation(showings: Showings)
}
