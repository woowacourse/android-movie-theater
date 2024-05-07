package woowacourse.movie.presentation.homefragments.movieList

import woowacourse.movie.repository.MovieRepository

class MovieListPresenter(private val view: MovieListContract.View) :
    MovieListContract.Presenter {
    private val movieRepository = MovieRepository()

    override fun loadMovies() {
        val movies = movieRepository.getAllMovies()
        view.displayMovies(movies)
    }
}
