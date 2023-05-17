package woowacourse.movie.presentation.view.main.home.theater

import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.Theater

interface TheaterContract {
    interface View {
        fun showTheatersView(theaters: List<Theater>, movieSchedule: List<List<String>>)
        fun finishErrorView()
    }

    interface Presenter {
        fun getTheaters()
    }
}