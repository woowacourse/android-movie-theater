package woowacourse.movie.activity.moviedetail

import android.os.Bundle
import com.woowacourse.domain.TheaterMovie
import com.woowacourse.domain.movie.Movie
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieSchedule
import woowacourse.movie.DateFormatter
import woowacourse.movie.model.toPresentation
import java.time.LocalDate

class MovieDetailPresenter(
    val view: MovieDetailContract.View,
    private val movieSchedule: MovieSchedule,
) : MovieDetailContract.Presenter {

    private var peopleCount = MIN_TICKET

    override fun loadScheduleDate(savedInstanceState: Bundle?) {
        view.setUpSpinner(movieSchedule, savedInstanceState)
    }

    override fun loadMovieData(movieData: Movie) {
        view.setUpMovieDetailView(movieData.toPresentation())
    }

    override fun addPeople() {
        peopleCount += 1
        view.setCountText(peopleCount)
    }

    override fun subPeople() {
        if (peopleCount == MIN_TICKET) {
            view.showGuideMessage(CANT_LOWER_ONE)
        } else {
            peopleCount -= 1
            view.setCountText(peopleCount)
        }
    }

    override fun loadMovieBookingInfo(
        movieData: TheaterMovie,
        selectDate: LocalDate,
        selectTime: String,
    ) {
        val movieBookingInfo = MovieBookingInfo(
            movieData,
            DateFormatter.format(selectDate),
            selectTime,
            peopleCount,
        )
        view.setIntent(movieBookingInfo)
    }

    companion object {
        private const val MIN_TICKET = 1
        private const val CANT_LOWER_ONE = "1장 이상의 표를 선택해 주세요."
    }
}
