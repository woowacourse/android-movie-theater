package woowacourse.movie.view.movieDetail

import woowacourse.movie.model.MovieModel
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

        fun setScreeningTimes(screeningTimes: List<LocalTime>)
    }
}
