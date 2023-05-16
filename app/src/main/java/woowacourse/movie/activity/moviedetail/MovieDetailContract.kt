package woowacourse.movie.activity.moviedetail

import com.woowacourse.domain.ScreeningSchedule
import com.woowacourse.domain.TheaterMovie
import com.woowacourse.domain.movie.Movie
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieSchedule
import woowacourse.movie.model.MovieUIModel
import java.time.LocalDate

interface MovieDetailContract {

    interface View {
        var presenter: Presenter
        fun setScheduleDate(schedule: MovieSchedule)
        fun setUpMovieDetailView(movieData: MovieUIModel)
        fun setCountText(count: Int)
        fun showGuideMessage(msg: String)
        fun setIntent(movieBookingInfo: MovieBookingInfo)
    }

    interface Presenter {
        fun loadScheduleDate(screeningSchedule: ScreeningSchedule)
        fun loadMovieData(movieData: Movie)
        fun addPeople()
        fun subPeople()
        fun loadMovieBookingInfo(
            movieData: TheaterMovie,
            selectDate: LocalDate,
            selectTime: String,
        )
    }
}
