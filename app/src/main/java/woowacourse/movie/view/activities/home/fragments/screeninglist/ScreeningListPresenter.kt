package woowacourse.movie.view.activities.home.fragments.screeninglist

import woowacourse.movie.repository.ScreeningRepository

class ScreeningListPresenter(
    private val view: ScreeningListContract.View,
    private val screeningRepository: ScreeningRepository
) : ScreeningListContract.Presenter {

    override fun loadScreenings() {
        val screenings = screeningRepository.findAll()
        view.setScreeningList(ScreeningListUIState.from(screenings))
    }
}
