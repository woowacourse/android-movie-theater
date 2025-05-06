package woowacourse.movie.ui.movielist.contract

import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters

interface TheaterBottomSheetDialogContract {
    interface Presenter {
        fun loadAvailableTheaters(movieId: Long)

        fun startBooking(theater: Theater)
    }

    interface View {
        fun showTheaters(theaters: Theaters)

        fun showReservation(
            theater: Theater,
            movieId: Long,
        )
    }
}
