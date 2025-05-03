package woowacourse.movie.presentation.theater

import woowacourse.movie.domain.model.Screening

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<Screening>)

        fun navigateToBooking(screening: Screening)
    }

    interface Presenter {
        fun loadTheaterList()

        fun startBooking(screening: Screening)
    }
}
