package woowacourse.movie.view.activities.screeningdetail

import woowacourse.movie.repository.Screening1Repository
import woowacourse.movie.repository.TheaterRepository

class ScreeningDetailPresenter(
    private val view: ScreeningDetailContract.View,
    private val theaterRepository: TheaterRepository,
    private val screeningRepository: Screening1Repository
) : ScreeningDetailContract.Presenter {

    override fun loadScreeningData(screeningId: Long, theaterId: Long) {
        val screening = screeningRepository.findById(screeningId) ?: return
        val theater = theaterRepository.findById(theaterId) ?: return
        view.setScreening(ScreeningDetailUIState.of(screening, theater))
    }

}
