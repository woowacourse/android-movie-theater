package woowacourse.movie.presentation.activities.main.contract.presenter

import woowacourse.movie.presentation.activities.main.contract.MainContract
import woowacourse.movie.presentation.model.mainstate.MainScreenState
import woowacourse.movie.presentation.model.mainstate.MainShowState

class MainPresenter(
    private var state: MainScreenState = MainScreenState(),
) : MainContract.Presenter() {

    override fun attach(view: MainContract.View) {
        super.attach(view)
        requireView().initView()
    }

    override fun getState(): MainScreenState = state.copy()

    override fun setState(state: MainScreenState) {
        this.state = state.copy()

        when (state.latestScreen) {
            MainShowState.Home -> requireView().showHomeScreen()
            MainShowState.History -> requireView().showHistoryScreen()
            MainShowState.Setting -> requireView().showSettingScreen()
        }
    }

    override fun onShowHistoryScreen() {
        state = state.copy(isShownHistory = true, latestScreen = MainShowState.History)
        requireView().showHistoryScreen()
    }

    override fun onShowHomeScreen() {
        state = state.copy(latestScreen = MainShowState.Home)
        requireView().showHomeScreen()
    }

    override fun onShowSettingScreen() {
        state = state.copy(latestScreen = MainShowState.Setting)
        requireView().showSettingScreen()
    }

    override fun wasShownHistory(): Boolean = state.isShownHistory
}
