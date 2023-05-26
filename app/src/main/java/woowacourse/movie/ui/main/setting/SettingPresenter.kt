package woowacourse.movie.ui.main.setting

import woowacourse.movie.repository.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: SettingRepository
) : SettingContract.Presenter {

    override fun getNotificationState() {
        view.updateSwitch(repository.getNotificationState())
    }

    override fun setSwitchState(isChecked: Boolean) {
        repository.setNotificationState(isChecked)
    }
}
