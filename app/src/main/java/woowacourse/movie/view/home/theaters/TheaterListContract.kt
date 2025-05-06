package woowacourse.movie.view.home.theaters

import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.home.model.ScreeningInfo

interface TheaterListContract {
    interface View {
        fun showTheaters(theaters: Theaters)

        fun moveToBooking(screeningInfo: ScreeningInfo)
    }

    interface Presenter {
        fun loadTheaters(movieId: Int)

        fun selectTheater(
            movieId: Int,
            selectedTheater: Theater,
        )
    }
}
