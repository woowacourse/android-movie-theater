package woowacourse.movie.ui.reservation

import woowacourse.movie.domain.MovieContent
import woowacourse.movie.domain.ReservationCount
import woowacourse.movie.domain.Theater
import woowacourse.movie.ui.HandleError
import java.time.LocalDate
import java.time.LocalTime

interface MovieReservationContract {
    interface View : HandleError {
        fun updateReservationCount(reservationCount: Int)

        fun moveMovieSeatSelectionPage(reservationDetail: ReservationDetail)

        fun showScreeningContent(
            movieContent: MovieContent,
            theater: Theater,
        )
    }

    interface Presenter {
        fun loadScreeningContent(
            movieContentId: Long,
            theaterId: Long,
        )

        fun updateReservationCount(count: Int = ReservationCount.DEFAULT_VALUE)

        fun selectDate(date: LocalDate)

        fun selectTime(time: LocalTime)

        fun decreaseCount()

        fun increaseCount()

        fun reserveSeat()

        fun handleError(throwable: Throwable)
    }
}
