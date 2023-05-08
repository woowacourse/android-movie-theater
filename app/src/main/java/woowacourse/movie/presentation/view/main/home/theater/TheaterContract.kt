package woowacourse.movie.presentation.view.main.home.theater

import woowacourse.movie.presentation.model.Theater

interface TheaterContract {
    interface View {
        fun showTheatersView(theaters: List<Theater>, movieSchedule: List<List<String>>)
    }

    interface Presenter {
        fun getTheaters(movieName: String)
    }
}