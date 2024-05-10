package woowacourse.movie.movielist

import woowacourse.movie.movielist.uimodel.AdvertisementUiModel
import woowacourse.movie.movielist.uimodel.ListItemUiModel
import woowacourse.movie.movielist.uimodel.MovieUiModel
import woowacourse.movie.movielist.uimodel.toAdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toMovieUiModel
import woowacourse.movie.repository.EverythingRepository

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val repository: EverythingRepository,
) : MovieListContract.Presenter {
    override fun loadContents() {
        val movieUiModels = repository.movies().map { it.toMovieUiModel() }
        val advertisementUiModels = repository.advertisements().map { it.toAdvertisementUiModel() }
        val mixedList = makeMixedList(movieUiModels, advertisementUiModels)
        view.showContents(mixedList)
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
