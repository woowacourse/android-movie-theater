package woowacourse.movie.ui.home

import woowacourse.movie.model.data.MovieContents
import woowacourse.movie.model.movie.MovieContent

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val movieContents: MovieContents,
) :
    TheaterSelectionContract.Presenter {
    private lateinit var movieContent: MovieContent

    override fun loadTheaters(movieContentId: Long) {
        movieContent =
            runCatching {
                movieContents.find(movieContentId)
            }.onFailure {
                view.showError(it)
            }.getOrThrow()
        view.showTheaters(movieContent.theaters)
    }

    override fun startReservation(
        movieContentId: Long,
        theaterId: Long,
    ) {
        view.navigateToMovieReservation(movieContentId, theaterId)
    }
}
