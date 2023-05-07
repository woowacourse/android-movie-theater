package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.data.TheaterRepository
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.TheaterScreeningState

class TheaterPresenter(
    val view: TheaterContract.View,
    private val theaterRepository: TheaterRepository
) : TheaterContract.Presenter {
    override fun loadTheatersData(movie: MovieState) {
        val theaters = theaterRepository.getScreeningMovieTheaters(movie)
        view.setTheaterAdapter(theaters.map { it.toItemModel { clickTheater(it, movie) } })
    }

    override fun clickTheater(theater: TheaterScreeningState, movie: MovieState) {
        val theaterMovie = SelectTheaterAndMovieState(
            theater.theater,
            movie,
            theater.screeningInfos.find { it.movie == movie }?.screeningDateTimes ?: listOf()
        )
        view.selectTheater(theaterMovie)
    }
}
