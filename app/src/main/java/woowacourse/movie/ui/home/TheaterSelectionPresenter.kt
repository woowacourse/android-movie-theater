package woowacourse.movie.ui.home

import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.data.database.theater.TheaterDao
import woowacourse.movie.domain.MovieContent
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.mapper.toMovieContent
import woowacourse.movie.domain.mapper.toTheater
import kotlin.concurrent.thread

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieContentDataSource: MovieContentDao,
    private val theaterDataSource: TheaterDao,
) :
    TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieContentId: Long) {
        runCatching {
            lateinit var movieContent: MovieContent
            lateinit var theaters: List<Theater>
            thread {
                movieContent = movieContentDataSource.find(movieContentId).toMovieContent()
                theaters =
                    movieContent.theaterIds.map { theaterId ->
                        theaterDataSource.find(theaterId).toTheater()
                    }
            }.join()
            view.showTheaters(movieContentId, theaters)
        }.onFailure {
            view.showError(it)
        }
    }
}
