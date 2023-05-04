package woowacourse.movie.presentation.movielist

import woowacourse.movie.data.MovieData
import woowacourse.movie.presentation.mappers.toPresentation

class MovieListPresenter(private val view: MovieListContract.View) : MovieListContract.Presenter {

    private val movies = MovieData.movies.toList()

    override fun requestMovies() = view.setMoviesAdapter(movies.map { it.toPresentation() })
}
