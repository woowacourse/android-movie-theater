package woowacourse.movie.presentation.movielist.movie

import woowacourse.movie.data.MovieItemData

class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {

    private val movieItems = MovieItemData.getMovieItems()

    override fun setMovies() {
        view.setMoviesAdapter(movieItems)
    }
}
