package woowacourse.movie.presentation.views.main.contract.presenter

import woowacourse.movie.presentation.model.mainstate.MainScreenState
import woowacourse.movie.presentation.model.mainstate.MainState
import woowacourse.movie.presentation.views.main.contract.MainContract

class MainPresenter(
    view: MainContract.View,
    private var state: MainState = MainState(),
) : MainContract.Presenter(view) {

    init {
        view.initView()
    }

    override fun getState(): MainState = state.copy()

    override fun setState(state: MainState) {
        this.state = state.copy()

        when (state.latestScreen) {
            MainScreenState.Home -> view.showHomeScreen()
            MainScreenState.History -> view.showHistoryScreen()
            MainScreenState.Setting -> view.showSettingScreen()
        }
    }

    override fun onShowHistoryScreen() {
        state = state.copy(isShownHistory = true, latestScreen = MainScreenState.History)
        view.showHistoryScreen()
    }

    override fun onShowHomeScreen() {
        state = state.copy(latestScreen = MainScreenState.Home)
        view.showHomeScreen()
    }

    override fun onShowSettingScreen() {
        state = state.copy(latestScreen = MainScreenState.Setting)
        view.showSettingScreen()
    }

    override fun wasShownHistory(): Boolean = state.isShownHistory
}
