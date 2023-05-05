package woowacourse.movie.contract.movielist

import woowacourse.movie.ui.model.AdModel
import woowacourse.movie.ui.model.MovieModel

interface MovieListContract {
    interface View {
        var presenter: Presenter

        fun setMovieList(movies: List<MovieModel>, ads: List<AdModel>)
    }

    interface Presenter {
        fun setupMovieList(movies: List<MovieModel>, ads: List<AdModel>)
        fun loadMovieList()
    }
}
