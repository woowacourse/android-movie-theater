package woowacourse.movie.screeningmovie

import woowacourse.movie.model.Movies
import woowacourse.movie.repository.MovieRepository

class ScreenMoviePresenter(
    private val view: ScreeningMovieContract.View,
    private val repository: MovieRepository,
) : ScreeningMovieContract.Presenter {
    override fun loadScreeningMovies() {
        val movies = repository.movies()
        val moviesWithAds =
            Movies(movies).insertAdvertisements(
                ADVERTISEMENT_INTERVAL,
            )
        view.showMovies(moviesWithAds.toScreenItems())
    }

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 3
    }
}
