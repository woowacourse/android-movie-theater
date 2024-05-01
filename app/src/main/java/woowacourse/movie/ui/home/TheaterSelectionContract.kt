package woowacourse.movie.ui.home

import woowacourse.movie.model.movie.Theater

interface TheaterSelectionContract {
    interface View {
        fun showTheaters(theaters: List<Theater>)

        fun navigateToMovieReservation(
            movieContentId: Long,
            theaterId: Long,
        )

        fun dismissDialog()
    }

    interface Presenter {
        fun loadTheaters(movieContentId: Long)

        fun startReservation(
            movieContentId: Long,
            theaterId: Long,
        )
    }
}
