package woowacourse.movie.view.home.movies

import woowacourse.movie.view.home.model.FeedUiModel

interface MovieListContract {
    interface View {
        fun showMovieList(movieList: List<FeedUiModel>)

        fun moveToTheaterSelection(movieId: Int)
    }

    interface Presenter {
        fun loadMovies()

        fun selectMovie(movieId: Int)
    }
}
