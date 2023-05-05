package woowacourse.movie.ui.adv

import woowacourse.movie.model.AdvState

class AdvDetailPresenter(
    private val view: AdvDetailContract.View
) : AdvDetailContract.Presenter {

    override fun init(advState: AdvState) {
        view.setAdv(advState)
    }
}
