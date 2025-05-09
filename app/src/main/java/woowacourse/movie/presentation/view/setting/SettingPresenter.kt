package woowacourse.movie.presentation.view.setting

import woowacourse.movie.data.SettingPreferenceManager

class SettingPresenter(
    private val view: SettingContract.View,
    private val preferenceManager: SettingPreferenceManager,
) : SettingContract.Presenter {
    override fun fetchSettingInfo() {
        val isEnabled = preferenceManager.getPushAlarmEnabled()
        view.showPushAlarmSetting(isEnabled)
    }

    override fun savePushAlarmSetting(isEnabled: Boolean) {
        preferenceManager.setPushAlarmEnabled(isEnabled)
    }
}
