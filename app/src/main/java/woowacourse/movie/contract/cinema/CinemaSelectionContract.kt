package woowacourse.movie.contract.cinema

import woowacourse.movie.domain.cinema.Cinema

interface CinemaSelectionContract {
    interface Presenter {
        fun presentCinemas()
    }

    interface View {
        fun setCinemas(cinemas: List<Cinema>)
    }
}
