package woowacourse.movie.presentation.ui.main.contract

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
        abstract fun changeHistoryState()
        abstract fun changeHomeState()
        abstract fun changeSettingState()
        abstract fun wasShownHistory(): Boolean
    }
}
