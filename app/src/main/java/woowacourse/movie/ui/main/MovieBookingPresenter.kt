package woowacourse.movie.ui.main

import woowacourse.movie.utils.Destination

class MovieBookingPresenter(
    private val view: MovieBookingContract.View,
) : MovieBookingContract.Presenter {
    private lateinit var destination: Destination

    override fun handleDestination(restoreDestination: Destination?) {
        destination = restoreDestination ?: Destination.HOME

        when (destination) {
            Destination.HISTORY -> {
                view.showHistory()
                view.showBottomSheetForHistory()
            }

            else -> {
                view.showHome()
                view.showBottomSheetForHome()
            }
        }
    }
}
