package woowacourse.movie.model.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.seat.grade.SeatGrade
import woowacourse.movie.model.seat.grade.SeatGradePolicy
import woowacourse.movie.model.seat.index.Col
import woowacourse.movie.model.seat.index.Row

@Parcelize
data class Seat(
    val row: Row,
    val col: Col,
) : Parcelable {
    fun getGrade(seatGradePolicy: SeatGradePolicy): SeatGrade = seatGradePolicy.getGrade(this)
}
