package woowacourse.movie.ui.main

import woowacourse.movie.utils.Destination

interface MovieBookingContract {
    interface Presenter {
        fun handleDestination(restoreDestination: Destination?)
    }

    interface View {
        fun showHistory()

        fun showHome()

        fun showSettings()

        fun showBottomSheetForHome()

        fun showBottomSheetForHistory()

        fun showBottomSheetForSettings()
    }
}
