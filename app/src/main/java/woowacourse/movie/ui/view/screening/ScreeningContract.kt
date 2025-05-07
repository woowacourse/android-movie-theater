package woowacourse.movie.ui.view.screening

import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ScreeningContent

interface ScreeningContract {
    interface Presenter {
        fun presentScreeningContents()

        fun selectScreening(screening: Screening)
    }

    interface View {
        fun setScreeningContents(screeningContents: List<ScreeningContent>)

        fun showCinemas(screening: Screening)
    }
}
