package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.data.TheaterRepository
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TheaterMovieState
import woowacourse.movie.model.TheaterState

class TheaterPresenter(
    val view: TheaterContract.View,
    private val theaterRepository: TheaterRepository
) : TheaterContract.Presenter {
    override fun loadTheatersData(movie: MovieState) {
        val theaters = theaterRepository.getScreeningMovieTheaters(movie)
        view.setTheaterAdapter(theaters.map { it.toItemModel { clickTheater(it, movie) } })
    }

    override fun clickTheater(theater: TheaterState, movie: MovieState) {
        val theaterMovie = TheaterMovieState(
            theater.theaterName,
            movie,
            theater.screenInfos.find { it.movieState == movie }?.dateTimes ?: listOf()
        )
        view.selectTheater(theaterMovie)
    }
}
