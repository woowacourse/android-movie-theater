package woowacourse.movie.feature.movieList.bottomSheet

import com.example.domain.usecase.GetTheaterScreeningInfoByMovieUseCase
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.TheaterScreeningInfoState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class TheaterPresenter(
    val view: TheaterContract.View,
    private val getTheaterScreeningInfoByMovieUseCase: GetTheaterScreeningInfoByMovieUseCase
) : TheaterContract.Presenter {
    override fun loadTheatersData(movie: MovieState) {
        getTheaterScreeningInfoByMovieUseCase(
            movie.asDomain(),
            onSuccess = {
                if (it.isEmpty()) {
                    view.loadTheaterIsEmpty()
                    return@getTheaterScreeningInfoByMovieUseCase
                }

                view.setTheaterAdapter(
                    it.map {
                        it.asPresentation().toItemModel { clickTheater(it, movie) }
                    }
                )
            },
            onFailure = { view.errorLoadTheaterData() }
        )
    }

    override fun clickTheater(theater: TheaterScreeningInfoState, movie: MovieState) {
        val theaterMovie = SelectTheaterAndMovieState(
            theater.theater,
            movie,
            theater.screeningInfos.find { it.movie == movie }?.screeningDateTimes ?: listOf()
        )
        view.selectTheater(theaterMovie)
    }
}
