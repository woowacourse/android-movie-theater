package woowacourse.movie.presentation.movielist.selecttheater

import woowacourse.movie.model.data.storage.MovieTheaterStorage

class SelectTheaterPresenter(
    override val view: SelectTheaterContract.View,
    private val movieTheaterStorage: MovieTheaterStorage,
    private val movieId: Long
) :
    SelectTheaterContract.Presenter {
    override fun getTheatersByMovieId(movieId: Long): List<String> =
        movieTheaterStorage.getTheatersByMovieId(movieId)

    override fun getTheaterTimeTableCountByMovieId(movieId: Long, theater: String): Int =
        movieTheaterStorage.getTheaterTimeTableByMovieId(movieId, theater).size

    override fun checkScreeningState() {
        view.setViewByScreeningState(getTheatersByMovieId(movieId).isNotEmpty())
    }
}
