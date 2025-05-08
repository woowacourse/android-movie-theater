package woowacourse.movie.contract.cinema

import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening

interface CinemaSelectionContract {
    interface Presenter {
        fun presentCinemas()

        fun onSelectCinema(cinema: Cinema)
    }

    interface View {
        fun setCinemas(cinemas: List<Cinema>)

        fun navigateToReservationScreen(
            screening: Screening,
            cinema: Cinema,
        )
    }
}
