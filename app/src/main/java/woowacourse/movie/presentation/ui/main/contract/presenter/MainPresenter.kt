package woowacourse.movie.presentation.ui.main.contract.presenter

import woowacourse.movie.presentation.model.mainstate.MainScreenState
import woowacourse.movie.presentation.model.mainstate.MainState
import woowacourse.movie.presentation.ui.main.contract.MainContract

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
            MainScreenState.History -> view.showHistoryScreen()
            MainScreenState.Home -> view.showHomeScreen()
            MainScreenState.Setting -> view.showSettingScreen()
        }
    }

    override fun changeHistoryState() {
        state = state.copy(isShownHistory = true, latestScreen = MainScreenState.History)
        view.showHistoryScreen()
    }

    override fun changeHomeState() {
        state = state.copy(latestScreen = MainScreenState.Home)
        view.showHomeScreen()
    }

    override fun changeSettingState() {
        state = state.copy(latestScreen = MainScreenState.Setting)
        view.showSettingScreen()
    }

    override fun wasShownHistory(): Boolean = state.isShownHistory
}
