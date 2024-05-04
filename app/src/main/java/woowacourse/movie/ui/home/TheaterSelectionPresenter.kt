package woowacourse.movie.ui.home

import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.Theater

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieContents: DefaultMovieDataSource<Long, MovieContent>,
    private val theaters: DefaultMovieDataSource<Long, Theater>,
) :
    TheaterSelectionContract.Presenter {
    private lateinit var movieContent: MovieContent

    override fun loadTheaters(movieContentId: Long) {
        runCatching {
            movieContent = movieContents.find(movieContentId)
            val movieTheaters =
                movieContent.theaterIds.map { theaterId ->
                    theaters.find(theaterId)
                }
            view.showTheaters(movieContentId, movieTheaters)
        }.onFailure {
            view.dismissDialog()
        }
    }
}
