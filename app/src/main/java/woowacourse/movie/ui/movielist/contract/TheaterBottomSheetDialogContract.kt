package woowacourse.movie.ui.movielist.contract

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theaters

interface TheaterBottomSheetDialogContract {
    interface Presenter {
        fun loadEntireTheaters(): Theaters

        fun loadAvailableTheaters(movie: Movie)
    }

    interface View {
        fun showTheaters(theaters: Theaters)
    }
}
