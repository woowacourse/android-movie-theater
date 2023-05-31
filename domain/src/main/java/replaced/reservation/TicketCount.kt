package replaced.reservation

import woowacourse.movie.domain.seat.Column
import woowacourse.movie.domain.seat.Row

@JvmInline
value class TicketCount(val value: Int) {

    init {
        require(MINIMUM <= value)
        require(value <= MAXIMUM)
    }

    companion object {
        const val MAXIMUM = (Row.INDEX_MAXIMUM + 1) * (Column.INDEX_MAXIMUM + 1)
        const val MINIMUM = 1

        fun isValidate(value: Int): Boolean = value in MINIMUM..MAXIMUM
    }
}
