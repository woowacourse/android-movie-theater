package woowacourse.movie.feature.bottomseat

import android.util.Log
import com.example.domain.repository.TheaterRepository
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.TheaterScreeningInfoState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val theaterRepository: TheaterRepository
) : TheaterContract.Presenter {
    override fun loadTheatersData(movie: MovieState) {
        val theaterInfos =
            theaterRepository.getAllTheaters()
                .filter { theater -> movie.asDomain() in theater.screeningInfos.map { it.movie } }
                .map { it ->
                    it.copy(screeningInfos = it.screeningInfos.filter { movie.asDomain() == it.movie })
                }
        Log.d("otter66", "MovieState: $movie")
        Log.d("otter66", "theaterInfos: $theaterInfos")
        view.setTheaterItems(
            theaterInfos.map { it1 ->
                it1.asPresentation().toItemModel { it2 -> clickTheater(it2, movie) }
            }
        )
    }

    override fun clickTheater(theater: TheaterScreeningInfoState, movie: MovieState) {
        val theaterMovie = SelectTheaterAndMovieState(
            theater.theater,
            movie,
            theater.screeningInfos.find { it.movie == movie }?.screeningDateTimes ?: listOf()
        )
        view.selectTheater(theaterMovie)
        view.bottomSheetDismiss()
    }
}
