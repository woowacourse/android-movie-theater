package woowacourse.movie.domain.screening

import woowacourse.movie.domain.theater.Point
import woowacourse.movie.domain.theater.Theater
import java.time.LocalDateTime
import kotlin.properties.Delegates

class Screening1(
    val screeningRange: ScreeningRange,
    val timeTable: TimeTable,
    val movie: Movie
) {
    var id: Long? by Delegates.vetoable(null) { _, old, new ->
        old == null && new != null
    }

    fun reserve(
        screeningDateTime: LocalDateTime,
        theater: Theater,
        seatPoints: List<Point>
    ): Reservation {
        require(
            screeningDateTime in screeningRange && timeTable.screenOn(
                theater,
                screeningDateTime.toLocalTime()
            )
        ) {
            NOT_SCREEN_ON_INPUT_ERROR.format(theater.name, screeningDateTime)
        }

        return Reservation(movie, screeningDateTime, theater, seatPoints)
    }

    companion object {
        private const val NOT_SCREEN_ON_INPUT_ERROR = "%s 극장에서 %s에 상영하지 않습니다."
    }
}