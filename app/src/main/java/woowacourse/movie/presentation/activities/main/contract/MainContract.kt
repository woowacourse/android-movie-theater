package woowacourse.movie.presentation.activities.main.contract

import woowacourse.movie.presentation.model.mainstate.MainState

interface MainContract {
    interface View {
        val presenter: Presenter

        fun initView()
        fun showHistoryScreen()
        fun showHomeScreen()
        fun showSettingScreen()
    }

    abstract class Presenter {
        private var view: View? = null

        open fun attach(view: View) {
            this.view = view
        }

        fun detach() {
            view = null
        }

        fun requireView(): View =
            view ?: throw IllegalStateException("View is not attached")

        abstract fun getState(): MainState
        abstract fun setState(state: MainState)
        abstract fun onShowHistoryScreen()
        abstract fun onShowHomeScreen()
        abstract fun onShowSettingScreen()
        abstract fun wasShownHistory(): Boolean
    }
}
