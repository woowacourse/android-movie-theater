package woowacourse.movie.ui.reservation

import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.ReservationCount
import woowacourse.movie.model.movie.ReservationDetail
import woowacourse.movie.model.movie.Theater
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
