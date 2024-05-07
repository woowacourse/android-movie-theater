package woowacourse.movie.presentation.ui.main.home.bottom

import woowacourse.movie.domain.model.TheaterCount

interface BottomTheaterContract {
    interface View : BottomTheaterActionHandler {
        fun showBottomTheater(
            theaterCounts: List<TheaterCount>,
            movieId: Int,
        )
    }

    interface Presenter {
        fun fetchTheaterCounts(movieId: Int)
    }
}
