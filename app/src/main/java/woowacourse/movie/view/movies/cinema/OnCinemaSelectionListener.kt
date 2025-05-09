package woowacourse.movie.view.movies.cinema

import woowacourse.movie.domain.model.Screening

interface OnCinemaSelectionListener {
    fun onReserveButtonClick(screening: Screening)
}
