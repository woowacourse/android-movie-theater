package woowacourse.movie.presenter.reservation

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.theater.TheaterMovieSchedule
import java.time.LocalDate
import java.time.LocalTime

interface ReservationContract {
    interface View {
        fun setupDateAdapter(dates: List<LocalDate>)

        fun showTicketCount(count: Int)

        fun showMovieInfo(movie: Movie)

        fun showErrorToastMessage(message: String)

        fun showSeatSelectionView(movieToReserve: MovieToReserve)

        fun updateTimes(times: List<LocalTime>)

        fun showSelectedDate(position: Int)

        fun showSelectedTime(position: Int)
    }

    interface Presenter {
        fun updateMovieData(theaterMovieSchedule: TheaterMovieSchedule)

        fun increaseTicketCount()

        fun decreaseTicketCount()

        fun onMovieToReserveRequest()

        fun updateMovieDate(date: LocalDate)

        fun updateMovieTime(time: LocalTime)

        fun updateTicketCount(count: Int?)

        fun updateSelectedDatePosition(position: Int)

        fun updateSelectedTimePosition(position: Int)
    }
}
