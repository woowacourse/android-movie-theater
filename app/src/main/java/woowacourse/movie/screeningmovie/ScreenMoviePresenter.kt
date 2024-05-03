package woowacourse.movie.screeningmovie

import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.screeningmovie.uimodel.AdvertisementUiModel
import woowacourse.movie.screeningmovie.uimodel.ListItemUiModel
import woowacourse.movie.screeningmovie.uimodel.ScreenMovieUiModel

class ScreenMoviePresenter(
    private val view: ScreeningMovieContract.View,
    private val repository: MovieRepository,
) : ScreeningMovieContract.Presenter {
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
