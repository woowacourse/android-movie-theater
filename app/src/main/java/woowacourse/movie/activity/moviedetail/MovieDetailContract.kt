package woowacourse.movie.activity.moviedetail

import com.woowacourse.domain.ScreeningSchedule
import com.woowacourse.domain.TheaterMovie
import com.woowacourse.domain.movie.Movie
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieSchedule
import java.time.LocalDate

interface MovieDetailContract {

    interface View {
        var presenter: Presenter
        fun setScheduleDate(schedule: MovieSchedule)
        fun initView(movieData: Movie)
        fun setCountText(count: Int)
        fun showGuideMessage(msg: String)
        fun setIntent(movieBookingInfo: MovieBookingInfo)
    }

    interface Presenter {
        fun getScheduleDate(screeningSchedule: ScreeningSchedule)
        fun initView(movieData: Movie)
        fun addPeople()
        fun subPeople()
        fun getMovieBookingInfo(
            movieData: TheaterMovie,
            selectDate: LocalDate,
            selectTime: String,
        )
    }
}
