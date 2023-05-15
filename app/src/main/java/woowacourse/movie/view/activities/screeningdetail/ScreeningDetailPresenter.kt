package woowacourse.movie.view.activities.screeningdetail

import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.view.PosterResourceProvider

class ScreeningDetailPresenter(
    private val view: ScreeningDetailContract.View,
    private val screeningId: Long,
    private val screeningRepository: ScreeningRepository
) : ScreeningDetailContract.Presenter {

    override fun loadScreeningData() {
        val screening = screeningRepository.findById(screeningId) ?: return
        view.setScreening(
            ScreeningDetailUIState.of(
                screening,
                PosterResourceProvider.getPosterResourceId(screening)
            )
        )
    }

}
