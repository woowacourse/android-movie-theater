package woowacourse.movie.ui.home

import woowacourse.movie.model.data.MovieContents
import woowacourse.movie.model.data.Theaters
import woowacourse.movie.model.movie.MovieContent

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieContents: MovieContents,
    private val theaters: Theaters,
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
