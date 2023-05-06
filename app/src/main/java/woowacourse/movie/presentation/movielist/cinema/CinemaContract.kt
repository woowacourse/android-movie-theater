package woowacourse.movie.presentation.movielist.cinema

import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel

interface CinemaContract {
    interface View {
        val presenter: Presenter
        fun setCinemaItemAdapter(cinemaModels: List<CinemaModel>)
    }

    interface Presenter {
        fun setCinemaModels(movieModel: MovieModel)
    }
}
