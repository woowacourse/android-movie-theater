package woowacourse.movie.ui.adv

import woowacourse.movie.model.AdvState

class AdvDetailPresenter(
    view: AdvDetailContract.View,
    advState: AdvState
) : AdvDetailContract.Presenter {
    init {
        view.setAdv(advState)
    }
}
