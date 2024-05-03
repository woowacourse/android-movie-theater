package woowacourse.movie.home.presenter

import woowacourse.movie.data.MovieRepository.getAllAdvertisements
import woowacourse.movie.data.MovieRepository.getAllMovies
import woowacourse.movie.home.presenter.contract.MovieHomeContract

class MovieHomePresenter(private val movieHomeContractView: MovieHomeContract.View) :
    MovieHomeContract.Presenter {
    override fun loadMovies() {
        val movies = getAllMovies()
        val advertisements = getAllAdvertisements()
        movieHomeContractView.displayMovies(movies, advertisements)
    }
}
