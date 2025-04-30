package woowacourse.movie.contract.cinema

import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy

interface CinemaSelectionContract {
    interface Presenter {
        fun presentCinemas()

        fun onSelectCinema(
            cinemaName: String,
            showtimePolicy: ShowtimePolicy,
        )
    }

    interface View {
        fun setCinemas(cinemas: List<Cinema>)

        fun navigateToReservationScreen(
            screening: Screening,
            cinemaName: String,
            showtimePolicy: ShowtimePolicy,
        )
    }
}
