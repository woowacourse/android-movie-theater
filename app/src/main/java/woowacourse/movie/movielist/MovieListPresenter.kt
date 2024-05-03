package woowacourse.movie.movielist

import woowacourse.movie.movielist.uimodel.AdvertisementUiModel
import woowacourse.movie.movielist.uimodel.ListItemUiModel
import woowacourse.movie.movielist.uimodel.ScreenMovieUiModel
import woowacourse.movie.movielist.uimodel.toAdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toScreenMovieUiModel
import woowacourse.movie.repository.MovieRepository

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val repository: MovieRepository,
) : MovieListContract.Presenter {
    override fun startReservation(screeningMovieId: Long) {
        val screenMovie = repository.screeningById(screeningMovieId)
        view.showTheaters(screenMovie.id)
    }

    override fun loadScreeningMovies() {
        val movieUiModels = repository.movies().map { it.toScreenMovieUiModel() }
        val advertisementUiModels = repository.advertisements().map { it.toAdvertisementUiModel() }
        val mixedList = makeMixedList(movieUiModels, advertisementUiModels)
        view.showMovies(mixedList)
    }

    private fun makeMixedList(
        movieUiModels: List<ScreenMovieUiModel>,
        advertisementUiModels: List<AdvertisementUiModel>,
        interval: Int = ADVERTISEMENT_INTERVAL,
    ): List<ListItemUiModel> {
        val advertisementDeque = ArrayDeque(advertisementUiModels)

        return movieUiModels.chunked(interval) { chunk ->
            if (chunk.size == interval && advertisementDeque.isNotEmpty()) {
                chunk + advertisementDeque.removeFirst()
            } else {
                chunk
            }
        }.flatten()
    }

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 3
    }
}
