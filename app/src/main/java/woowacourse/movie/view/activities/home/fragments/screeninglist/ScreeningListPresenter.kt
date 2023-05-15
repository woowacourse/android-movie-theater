package woowacourse.movie.view.activities.home.fragments.screeninglist

import woowacourse.movie.R
import woowacourse.movie.domain.screening.Screening
import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.view.PosterResourceProvider

class ScreeningListPresenter(private val view: ScreeningListContract.View):
    ScreeningListContract.Presenter {

    override fun loadScreenings() {
        val screenings = ScreeningRepository.findAll()
        val screeningListViewItemUIStates = createScreeningListViewItemUIStates(screenings)
        view.setScreeningList(screeningListViewItemUIStates)
    }

    private fun createScreeningListViewItemUIStates(screenings: List<Screening>): List<ScreeningListViewItemUIState> {
        val screeningListViewUIStates = mutableListOf<ScreeningListViewItemUIState>()

        screenings.forEachIndexed { index, screening ->
            screeningListViewUIStates.add(
                ScreeningUIState.of(
                    screening,
                    PosterResourceProvider.getPosterResourceId(screening)
                )
            )
            if ((index + 1) % ADVERTISE_INTERVAL == 0)
                screeningListViewUIStates.add(AdvertisementUIState(R.drawable.ad_image))
        }

        return screeningListViewUIStates
    }

    companion object {
        private const val ADVERTISE_INTERVAL = 3
    }
}
