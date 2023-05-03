package woowacourse.movie.ui.main.cinemaBottomSheet

import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState

interface CinemaListContract {
    interface View {
        fun setAdapter(cinemas: List<CinemaState>)
    }
    interface Presenter {
        fun getCinemaList(movie: MovieState)
    }
}
