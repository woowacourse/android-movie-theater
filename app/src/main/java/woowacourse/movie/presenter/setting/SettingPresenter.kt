package woowacourse.movie.presenter.setting

import android.content.SharedPreferences
import androidx.core.content.edit

class SettingPresenter(
    private val view: SettingContracts.View,
    private val prefs: SharedPreferences,
) : SettingContracts.Presenter {
    override fun changeAlarmSwitch(isChecked: Boolean) {
        prefs.edit { putBoolean(PUSH_ENABLED_DATA_KEY, isChecked) }
    }

    override fun loadAlarmSwitch() {
        val isPushEnabled = prefs.getBoolean(PUSH_ENABLED_DATA_KEY, false)
        view.updateAlarmSwitchView(isPushEnabled)
    }

    companion object {
        const val PUSH_ENABLED_DATA_KEY = "push_enabled"
    }
}
