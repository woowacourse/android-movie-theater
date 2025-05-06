package woowacourse.movie.ui.presenter

import woowacourse.movie.ui.contract.MainContract
import woowacourse.movie.ui.view.MainScreen

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun presentScreen(mainScreen: MainScreen) {
        view.updateScreen(mainScreen)
    }
}
