package woowacourse.movie.feature.home.movie

import woowacourse.movie.data.MovieRepository.getAllMovies

class MovieHomePresenter(private val movieHomeContractView: MovieHomeContract.View) :
    MovieHomeContract.Presenter {
    override fun loadMovies() {
        val movies = getAllMovies()
        movieHomeContractView.displayMovies(movies)
    }
}
