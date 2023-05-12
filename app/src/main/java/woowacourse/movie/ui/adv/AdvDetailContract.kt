package woowacourse.movie.ui.adv

import woowacourse.movie.model.AdvState
import woowacourse.movie.ui.base.BaseContract

interface AdvDetailContract {
    interface View {
        fun showAdv(advState: AdvState)
    }

    interface Presenter : BaseContract.Presenter
}
