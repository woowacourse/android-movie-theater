package woowacourse.movie.ui.main.setting

import woowacourse.movie.repository.SettingRepository

class SettingPresenter(private val repository: SettingRepository) : SettingContract.Presenter {

    override fun getNotificationState(): Boolean {
        return repository.getNotificationState()
    }

    override fun setSwitchState(isChecked: Boolean) {
        repository.setNotificationState(isChecked)
    }
}
