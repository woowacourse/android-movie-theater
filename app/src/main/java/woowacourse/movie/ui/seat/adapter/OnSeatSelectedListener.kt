package woowacourse.movie.ui.seat.adapter

import woowacourse.movie.domain.model.Seat

interface OnSeatSelectedListener {
    fun onSeatSelection(
        seat: Seat,
        selection: Boolean,
    )
}
