package woowacourse.movie.presenter.movies

import woowacourse.movie.model.movie.Movie

class MoviesPresenter(
    private val view: MoviesContracts.View,
) : MoviesContracts.Presenter {
    override fun initView() {
        view.showMovies(Movie.values)
    }

    override fun onAdvertisementRequested(url: String) {
        view.showAdvertisement(url)
    }
}
