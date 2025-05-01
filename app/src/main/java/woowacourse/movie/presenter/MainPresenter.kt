package woowacourse.movie.presenter

import woowacourse.movie.contract.MainContract

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun presentScreen(screenId: Int) {
        view.updateScreen(screenId)
    }
}
