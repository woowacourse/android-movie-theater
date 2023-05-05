package woowacourse.movie.ui.adv

import woowacourse.movie.model.AdvState

interface AdvDetailContract {
    interface View {
        fun setAdv(advState: AdvState)
    }

    interface Presenter {
        fun init(advState: AdvState)
    }
}
