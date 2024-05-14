package woowacourse.movie.presentation.homefragments.movieList.bottomDialog

import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.homefragments.movieList.uimodel.ScreeningTheater
import woowacourse.movie.repository.TheaterListRepository

class TheaterBottomDialogPresenter(
    private val view: TheaterBottomDialogContract.View,
    private val repository: TheaterListRepository,
) : TheaterBottomDialogContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val theaterList: List<Theater> = repository.findTheaterList(movieId)

        val screeningTheaterList: List<ScreeningTheater> =
            theaterList.map { theater ->
                ScreeningTheater(
                    movieId = movieId,
                    theaterId = theater.id,
                    theaterName = theater.name,
                    numberOfScreeningSchedule = theater.findScreenScheduleListWithMovieId(movieId).size,
                )
            }

        // send copied List
        view.showTheaterList(screeningTheaterList.toList())
    }
}
