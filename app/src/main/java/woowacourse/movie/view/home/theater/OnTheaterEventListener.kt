package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.Showing

fun interface OnTheaterEventListener {
    fun onClickReservation(showing: Showing)
}
