package woowacourse.movie.activity.moviedetail

import android.os.Bundle
import com.woowacourse.domain.TheaterMovie
import com.woowacourse.domain.movie.Movie
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieSchedule
import woowacourse.movie.model.MovieUIModel
import java.time.LocalDate

interface MovieDetailContract {

    interface View {
        var presenter: Presenter
        fun setUpSchedules(movieSchedule: MovieSchedule, savedInstanceState: Bundle?)
        fun setUpMovieDetailView(movieData: MovieUIModel)
        fun showCountText(count: Int)
        fun showGuideMessage(msg: String)
        fun navigateToSeatPicker(movieBookingInfo: MovieBookingInfo)
    }

    interface Presenter {
        fun loadScheduleDate(savedInstanceState: Bundle?)
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
