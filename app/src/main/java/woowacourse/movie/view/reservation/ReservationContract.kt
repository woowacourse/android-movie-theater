package woowacourse.movie.view.reservation

import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.ReservationOptions
import java.time.LocalDate
import java.time.LocalTime

interface ReservationContract {
    interface View {
        val presenter: Presenter
        fun getMovie(): MovieListModel.MovieUiModel
        fun initMovieView(movie: MovieListModel.MovieUiModel)
        fun setUpDateSpinner(screeningDates: List<LocalDate>)
        fun setUpTimeSpinner(screeningTimes: List<LocalTime>, selectedPosition: Int?)
        fun setPeopleCountTextView(count: Int)
        fun openSeatSelectionActivity(
            reservationOptions: ReservationOptions,
            movie: MovieListModel.MovieUiModel
        )
    }

    interface Presenter {
        fun loadMovie()
        fun setUpScreeningDateTime()
        fun increasePeopleCount()
        fun decreasePeopleCount()
        fun selectScreeningDate(date: LocalDate)
        fun selectScreeningTime(time: LocalTime, position: Int)
        fun saveTimePosition(position: Int)
        fun getReservationOptions(): ReservationOptions
        fun restoreReservationOptions(
            screeningDate: LocalDate,
            screeningTime: LocalTime,
            peopleCount: Int
        )

        fun setPeopleCount()
        fun reserve()
    }
}
