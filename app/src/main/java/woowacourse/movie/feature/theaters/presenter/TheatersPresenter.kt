package woowacourse.movie.feature.theaters.presenter

import woowacourse.movie.feature.model.ScreeningUiModel
import woowacourse.movie.feature.theaters.contract.TheatersContract

class TheatersPresenter(
    private val view: TheatersContract.View,
) : TheatersContract.Presenter {
    override fun selectTheater(screening: ScreeningUiModel) {
        view.navigateToBookingDetail(screening)
    }
}
