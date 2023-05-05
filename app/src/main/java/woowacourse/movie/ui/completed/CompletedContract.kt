package woowacourse.movie.ui.completed

import woowacourse.movie.model.main.MovieUiModel

interface CompletedContract {

    interface View {

        fun initView(movie: MovieUiModel, theaterName: String)
    }

    interface Presenter {

        fun initReservation()
    }
}
