package woowacourse.movie.presentation.activities.main.contract.presenter

import woowacourse.movie.presentation.activities.main.contract.MainContract
import woowacourse.movie.presentation.model.mainstate.MainScreenState
import woowacourse.movie.presentation.model.mainstate.MainState

class MainPresenter(
    private var state: MainState = MainState(),
) : MainContract.Presenter() {

    override fun attach(view: MainContract.View) {
        super.attach(view)
        requireView().initView()
    }

    override fun getState(): MainState = state.copy()

    override fun setState(state: MainState) {
        this.state = state.copy()

        when (state.latestScreen) {
            MainScreenState.Home -> requireView().showHomeScreen()
            MainScreenState.History -> requireView().showHistoryScreen()
            MainScreenState.Setting -> requireView().showSettingScreen()
        }
    }

    override fun onShowHistoryScreen() {
        state = state.copy(isShownHistory = true, latestScreen = MainScreenState.History)
        requireView().showHistoryScreen()
    }

    override fun onShowHomeScreen() {
        state = state.copy(latestScreen = MainScreenState.Home)
        requireView().showHomeScreen()
    }

    override fun onShowSettingScreen() {
        state = state.copy(latestScreen = MainScreenState.Setting)
        requireView().showSettingScreen()
    }

    override fun wasShownHistory(): Boolean = state.isShownHistory
}
