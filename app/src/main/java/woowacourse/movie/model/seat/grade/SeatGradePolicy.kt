package woowacourse.movie.model.seat.grade

import woowacourse.movie.model.seat.Seat

interface SeatGradePolicy {
    fun getGrade(seat: Seat): SeatGrade
}
