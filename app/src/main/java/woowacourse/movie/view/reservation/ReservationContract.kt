package woowacourse.movie.view.reservation

import woowacourse.movie.view.model.MovieUiModel
import java.time.LocalDate
import java.time.LocalTime

interface ReservationContract {
    interface View {
        fun setCount(count: Int)
        fun setViewData(movie: MovieUiModel, theaterName: String)
        fun showScreeningDate(screeningDates: List<LocalDate>)
        fun showScreeningTimes(screeningTimes: List<LocalTime>)
    }

    interface Presenter {

        fun fetchViewData()
        fun fetchScreeningDates()
        fun fetchScreeningTimes(date: LocalDate)
        fun minusCount()
        fun plusCount()
    }
}
