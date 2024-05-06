package woowacourse.movie.ui.home

import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.Theater

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieContentDataSource: DefaultMovieDataSource<Long, MovieContent>,
    private val theaterDataSource: DefaultMovieDataSource<Long, Theater>,
) :
    TheaterSelectionContract.Presenter {
    private lateinit var movieContent: MovieContent

    override fun loadTheaters(movieContentId: Long) {
        runCatching {
            movieContent = movieContentDataSource.find(movieContentId)
            val movieTheaters =
                movieContent.theaterIds.map { theaterId ->
                    theaterDataSource.find(theaterId)
                }
            view.showTheaters(movieContentId, movieTheaters)
        }.onFailure {
            view.showError(it)
        }
    }
}
