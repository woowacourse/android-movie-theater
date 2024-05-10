package woowacourse.movie.screeningmovie

import woowacourse.movie.model.Movies
import woowacourse.movie.repository.MovieRepository
import kotlin.concurrent.thread

class ScreenMoviePresenter(
    private val view: ScreeningMovieContract.View,
    private val repository: MovieRepository,
) : ScreeningMovieContract.Presenter {
    override fun loadScreeningMovies() {
        thread {
            val movies = repository.movies()
            val moviesWithAds =
                Movies(movies).insertAdvertisements(
                    ADVERTISEMENT_INTERVAL,
                )
            view.showMovies(moviesWithAds.toScreenItems())
        }.join()
    }

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 3
    }
}
