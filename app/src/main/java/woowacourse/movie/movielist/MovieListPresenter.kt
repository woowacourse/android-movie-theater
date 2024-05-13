package woowacourse.movie.movielist

import woowacourse.movie.model.data.AdvertisementRepository
import woowacourse.movie.movielist.uimodel.AdvertisementUiModel
import woowacourse.movie.movielist.uimodel.ListItemUiModel
import woowacourse.movie.movielist.uimodel.MovieUiModel
import woowacourse.movie.movielist.uimodel.toAdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toMovieUiModel
import woowacourse.movie.usecase.FetchAllMoviesUseCase
import woowacourse.movie.util.runOnOtherThreadAndReturn

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val advertisementRepository: AdvertisementRepository,
    private val fetchAllMoviesUseCase: FetchAllMoviesUseCase,
) : MovieListContract.Presenter {
    override fun loadContents() {
        runOnOtherThreadAndReturn { fetchAllMoviesUseCase() }.onSuccess { movies ->
            val movieUiModels = movies.map { it.toMovieUiModel() }
            val advertisementUiModels =
                advertisementRepository.advertisements().map { it.toAdvertisementUiModel() }
            val mixedList = makeMixedList(movieUiModels, advertisementUiModels)
            view.showContents(mixedList)
        }.onFailure {
            // view.showError()
        }
    }

    private fun makeMixedList(
        movieUiModels: List<MovieUiModel>,
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

    override fun selectMovie(movieId: Long) {
        view.showTheaters(movieId)
    }

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 3
    }
}
