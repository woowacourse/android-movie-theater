package woowacourse.movie.feature.theaters.contract

import woowacourse.movie.feature.model.ScreeningUiModel

class TheatersContract {
    interface View {
        fun navigateToBookingDetail(screening: ScreeningUiModel)
    }

    interface Presenter {
        fun selectTheater(screening: ScreeningUiModel)
    }
}
