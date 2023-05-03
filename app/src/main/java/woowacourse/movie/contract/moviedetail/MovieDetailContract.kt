package woowacourse.movie.contract.moviedetail

import woowacourse.movie.ui.model.MovieModel
import java.time.LocalDate
import java.time.LocalTime

interface MovieDetailContract {
    interface View {
        var presenter: Presenter

        fun setPeopleCount(count: Int)

        fun setDateSpinner(dates: List<LocalDate>)

        fun setTimeSpinner(times: List<LocalTime>)
    }

    interface Presenter {
        fun setPeopleCount(count: Int)

        fun plusPeopleCount()

        fun minusPeopleCount()

        fun setScreeningDates(movie: MovieModel)

        fun setScreeningTimes(date: LocalDate)
    }
}
