package woowacourse.movie.presentation.movielist.cinema

import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel

interface CinemaContract {
    interface View {
        val presenter: Presenter
        fun setCinemaModels(cinemaModels: List<CinemaModel>)
    }

    interface Presenter {
        fun setCinemaModels(movieModel: MovieModel)
    }
}
