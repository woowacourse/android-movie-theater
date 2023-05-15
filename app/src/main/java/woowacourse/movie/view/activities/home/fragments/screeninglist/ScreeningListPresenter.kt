package woowacourse.movie.view.activities.home.fragments.screeninglist

import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.TheatersUIState

class ScreeningListPresenter(
    private val view: ScreeningListContract.View,
    private val screeningRepository: ScreeningRepository
) : ScreeningListContract.Presenter {

    override fun loadScreenings() {
        val screenings = screeningRepository.findAll()
        view.setScreeningList(ScreeningListUIState.from(screenings))
    }

    override fun onReserveNow(screeningId: Long) {
        val screening = screeningRepository.findById(screeningId)
            ?: throw IllegalArgumentException("아이디가 ${screeningId}인 상영을 찾을 수 없습니다.")
        view.showTheaters(TheatersUIState.from(screening))
    }
}
