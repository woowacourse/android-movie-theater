package woowacourse.movie.presentation.movielist

import woowacourse.movie.data.MovieItemData

class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {

    private val movieItems = MovieItemData.getMovieItems()

    override fun requestMovies() {
        view.setMoviesAdapter(movieItems)
    }
}
