package woowacourse.movie.presentation.views.main.contract

import woowacourse.movie.presentation.model.mainstate.MainState

interface MainContract {
    interface View {
        val presenter: Presenter

        fun initView()
        fun showHistoryScreen()
        fun showHomeScreen()
        fun showSettingScreen()
    }

    abstract class Presenter(protected val view: View) {

        abstract fun getState(): MainState
        abstract fun setState(state: MainState)
        abstract fun onShowHistoryScreen()
        abstract fun onShowHomeScreen()
        abstract fun onShowSettingScreen()
        abstract fun wasShownHistory(): Boolean
    }
}
