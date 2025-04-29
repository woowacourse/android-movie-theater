package woowacourse.movie.presentation.theater

import woowacourse.movie.domain.model.ScreeningInfo

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<ScreeningInfo>)
        fun navigateToBooking(screeningInfo: ScreeningInfo)
    }

    interface Presenter {
        fun onViewCreated()
        fun onTheaterClicked(screeningInfo: ScreeningInfo)
    }
}