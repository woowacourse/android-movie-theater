package woowacourse.movie.ui.adv

import woowacourse.movie.model.AdvState
import woowacourse.movie.ui.BaseContract

interface AdvDetailContract {
    interface View {
        fun setAdv(advState: AdvState)
    }

    interface Presenter : BaseContract.Presenter {
        fun getAdv(advState: AdvState)
    }
}
