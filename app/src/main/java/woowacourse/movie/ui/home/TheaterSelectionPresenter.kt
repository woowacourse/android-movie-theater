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
        movieContent =
            runCatching {
                movieContents.find(movieContentId)
            }.onFailure {
                view.dismissDialog()
            }.getOrThrow()

        val movieTheaters =
            movieContent.theaterIds.map { theaterId ->
                theaters.find(theaterId)
            }
        view.showTheaters(movieTheaters)
    }

    override fun startReservation(
        movieContentId: Long,
        theaterId: Long,
    ) {
        view.navigateToMovieReservation(movieContentId, theaterId)
    }
}
