package woowacourse.movie.view.activities.screeningdetail

import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.repository.TheaterRepository

class ScreeningDetailPresenter(
    private val view: ScreeningDetailContract.View,
    private val screeningId: Long,
    private val theaterId: Long,
    private val theaterRepository: TheaterRepository,
    private val screeningRepository: ScreeningRepository
) : ScreeningDetailContract.Presenter {

    private val screening by lazy {
        screeningRepository.findById(screeningId)
            ?: throw IllegalArgumentException("아이디가 부여되지 않은 상영은 보여질 수 없음")
    }
    private val theater by lazy {
        theaterRepository.findById(theaterId)
            ?: throw IllegalArgumentException("아이디가 부여되지 않은 극장은 보여질 수 없음")
    }

    override fun loadScreeningData() {
        view.setScreening(ScreeningDetailUIState.of(screening, theater))
    }

}
