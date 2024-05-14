package woowacourse.movie.ui.home

import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.data.database.theater.TheaterDao
import woowacourse.movie.domain.MovieContent
import woowacourse.movie.domain.Theater
import woowacourse.movie.data.mapper.toMovieContent
import woowacourse.movie.data.mapper.toTheater
import kotlin.concurrent.thread

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieContentDataSource: MovieContentDao,
    private val theaterDataSource: TheaterDao,
) :
    TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieContentId: Long) {
        runCatching {
            var theaters: List<Theater>? = null
            thread {
                val movieContent = movieContentDataSource.find(movieContentId).toMovieContent()
                theaters =
                    movieContent.theaterIds.map { theaterId ->
                        theaterDataSource.find(theaterId).toTheater()
                    }
            }.join()
            view.showTheaters(movieContentId, theaters ?: throw IllegalStateException())
        }.onFailure {
            view.showError(it)
        }
    }
}
