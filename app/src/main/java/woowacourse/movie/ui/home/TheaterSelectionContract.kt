package woowacourse.movie.ui.home

import woowacourse.movie.model.movie.Theater

interface TheaterSelectionContract {
    interface View {
        fun showTheaters(
            movieContentId: Long,
            theaters: List<Theater>,
        )

        fun navigateToMovieReservation(
            movieContentId: Long,
            theaterId: Long,
        )

        fun showError()

        fun handleDialogError()
    }

    interface Presenter {
        fun loadTheaters(movieContentId: Long)
    }
}
