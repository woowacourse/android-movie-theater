package woowacourse.movie.view.activities.home.fragments.screeninglist

import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.ScreeningListUIState
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.TheatersUIState

interface ScreeningListContract {
    interface Presenter {
        fun loadScreenings()

        fun onReserveNow(screeningId: Long)
    }

    interface View {
        fun setScreeningList(screeningListUIState: ScreeningListUIState)

        fun showTheaters(theaters: TheatersUIState)
    }
}
