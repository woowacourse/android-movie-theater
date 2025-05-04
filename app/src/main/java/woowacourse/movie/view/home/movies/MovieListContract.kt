package woowacourse.movie.view.home.movies

import woowacourse.movie.view.home.model.UiModel

interface MovieListContract {
    interface View {
        fun showMovieList(movieList: List<UiModel>)

        fun moveToTheaterSelection(movieId: Int)
    }

    interface Presenter {
        fun loadMovies()

        fun selectMovie(movieId: Int)
    }
}
