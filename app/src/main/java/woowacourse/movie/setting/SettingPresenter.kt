package woowacourse.movie.setting

import woowacourse.movie.main.sharedPreference.PreferencesProvider

class SettingPresenter(
    private val view: SettingContract.View,
    private val preferences: PreferencesProvider,
) : SettingContract.Presenter {
    override fun setNotificationAlarm(isChecked: Boolean) {
        preferences.setAlarmEnabled(isChecked)
        view.showAlarmState()
    }
}
