package woowacourse.movie.ui.main.cinemaBottomSheet

import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.BaseContract

interface CinemaListContract {
    interface View {
        fun setAdapter(cinemas: List<CinemaState>)
    }
    interface Presenter : BaseContract.Presenter {
        fun getCinemaList(movie: MovieState)
    }
}
