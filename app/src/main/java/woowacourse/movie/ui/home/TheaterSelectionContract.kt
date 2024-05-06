package woowacourse.movie.ui.home

import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.HandleError

interface TheaterSelectionContract {
    interface View : HandleError {
        fun showTheaters(
            movieContentId: Long,
            theaters: List<Theater>,
        )

        fun navigateToMovieReservation(
            movieContentId: Long,
            theaterId: Long,
        )
    }

    interface Presenter {
        fun loadTheaters(movieContentId: Long)
    }
}
