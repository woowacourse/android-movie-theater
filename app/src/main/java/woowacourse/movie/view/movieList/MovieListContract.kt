package woowacourse.movie.view.movieList

import woowacourse.movie.model.AdModel
import woowacourse.movie.model.MovieModel

interface MovieListContract {
    interface View {
        var presenter: Presenter

        fun setMovieList(movies: List<MovieModel>, ads: List<AdModel>)
    }

    interface Presenter {
        fun loadMovieList()
    }
}
