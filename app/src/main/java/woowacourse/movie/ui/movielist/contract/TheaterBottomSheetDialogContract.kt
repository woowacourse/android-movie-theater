package woowacourse.movie.ui.movielist.contract

import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.theater.Theaters

interface TheaterBottomSheetDialogContract {
    interface Presenter {
        fun loadEntireTheaters(): Theaters

        fun loadAvailableTheaters(movie: Movie)
    }

    interface View {
        fun showTheaters(theaters: Theaters)
    }
}
