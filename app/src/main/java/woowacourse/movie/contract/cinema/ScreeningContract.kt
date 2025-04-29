package woowacourse.movie.contract.cinema

import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ScreeningContent

interface ScreeningContract {
    interface Presenter {
        fun presentScreeningContents()

        fun selectScreening(screening: Screening)
    }

    interface View {
        fun setScreeningContents(screenings: List<ScreeningContent>)

        fun showCinemas(screening: Screening)
    }
}
