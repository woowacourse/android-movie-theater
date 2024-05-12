package woowacourse.movie.feature.home.movie

import woowacourse.movie.data.movie.MovieRepositoryImpl

class MovieHomePresenter(private val movieHomeContractView: MovieHomeContract.View) :
    MovieHomeContract.Presenter {
    override fun loadMovies() {
        val movies = MovieRepositoryImpl.getAllMovies()
        movieHomeContractView.displayMovies(movies)
    }
}
