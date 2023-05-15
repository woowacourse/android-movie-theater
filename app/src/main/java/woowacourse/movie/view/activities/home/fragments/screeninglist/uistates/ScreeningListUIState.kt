package woowacourse.movie.view.activities.home.fragments.screeninglist.uistates

import woowacourse.movie.R
import woowacourse.movie.domain.screening.Screening

data class ScreeningListUIState(val screenings: List<ScreeningListViewItemUIState>) {

    companion object {
        private const val ADVERTISE_INTERVAL = 3

        fun from(screenings: List<Screening>): ScreeningListUIState {
            return ScreeningListUIState(createScreeningListViewItemUIStates(screenings))
        }

        private fun createScreeningListViewItemUIStates(screenings: List<Screening>): List<ScreeningListViewItemUIState> {
            val screeningListViewUIStates = mutableListOf<ScreeningListViewItemUIState>()

            screenings.forEachIndexed { index, screening ->
                screeningListViewUIStates.add(ScreeningUIState.of(screening))
                if ((index + 1) % ADVERTISE_INTERVAL == 0)
                    screeningListViewUIStates.add(AdvertisementUIState(R.drawable.ad_image))
            }

            return screeningListViewUIStates
        }
    }
}