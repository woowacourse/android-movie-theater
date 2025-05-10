package woowacourse.movie.presentation.theater

import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.movie.Movie

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<ScreeningInfo>)

        fun navigateToBooking(screeningInfo: ScreeningInfo)
    }

    interface Presenter {
        fun initializeTheater(movie: Movie)

        fun selectTheater(screeningInfo: ScreeningInfo)
    }
}
