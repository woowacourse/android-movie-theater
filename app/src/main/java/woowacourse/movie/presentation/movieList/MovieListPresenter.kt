package woowacourse.movie.presentation.movieList

import woowacourse.movie.repository.MovieRepository

class MovieListPresenter(private val movieListContractView: MovieListContract.View) : MovieListContract.Presenter {
    private val movieRepository = MovieRepository()

    override fun loadMovies() {
        val movies = movieRepository.getAllMovies()
        movieListContractView.displayMovies(movies)
    }
}
