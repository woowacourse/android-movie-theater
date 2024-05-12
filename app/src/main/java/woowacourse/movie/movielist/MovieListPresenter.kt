package woowacourse.movie.movielist

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater
import woowacourse.movie.movielist.uimodel.AdvertisementUiModel
import woowacourse.movie.movielist.uimodel.ListItemUiModel
import woowacourse.movie.movielist.uimodel.MovieUiModel
import woowacourse.movie.movielist.uimodel.toAdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toMovieUiModel
import woowacourse.movie.repository.EverythingRepository
import woowacourse.movie.usecase.FetchAllMoviesUseCase
import java.util.concurrent.FutureTask

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val repository: EverythingRepository,
    private val fetchAllMoviesUseCase: FetchAllMoviesUseCase,
) : MovieListContract.Presenter {
    override fun loadContents() {
        val task = FutureTask {
            fetchAllMoviesUseCase()
        }
        Thread(task).start()
        val result = task.get()
        result.onSuccess {movies ->
            val movieUiModels = movies.map { it.toMovieUiModel() }
            val advertisementUiModels =
                repository.advertisements().map { it.toAdvertisementUiModel() }
            val mixedList = makeMixedList(movieUiModels, advertisementUiModels)
            view.showContents(mixedList)
        }.onFailure {
            //view.showError()
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
