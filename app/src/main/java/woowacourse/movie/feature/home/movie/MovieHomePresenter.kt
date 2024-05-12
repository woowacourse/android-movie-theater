package woowacourse.movie.feature.home.movie

import woowacourse.movie.data.movie.MovieRepositoryImpl.getAllMovies

class MovieHomePresenter(private val movieHomeContractView: MovieHomeContract.View) :
    MovieHomeContract.Presenter {
    override fun loadMovies() {
        val movies = getAllMovies()
        movieHomeContractView.displayMovies(movies)
    }
}
