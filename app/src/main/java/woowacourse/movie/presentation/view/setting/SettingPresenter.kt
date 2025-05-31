package woowacourse.movie.presentation.view.setting

class SettingPresenter(
    private val view: SettingContract.View,
    private var isPushEnabled: Boolean = true,
) : SettingContract.Presenter {
    override fun checkPreference() {
        view.updateSwitch(isPushEnabled)
    }

    override fun updatePreference(enabled: Boolean) {
        isPushEnabled = enabled
        view.updateSwitch(enabled)
    }
}
