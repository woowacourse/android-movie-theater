package woowacourse.movie.feature.setting

import android.content.SharedPreferences
import androidx.core.content.edit

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreferences: SharedPreferences,
) : SettingContract.Presenter {
    override fun loadNotificationSettings() {
        val isNotificationEnabled = sharedPreferences.getBoolean("notification_enabled", false)
        view.setNotificationSwitchChecked(isNotificationEnabled)
    }

    override fun toggleNotificationSwitch(isChecked: Boolean) {
        sharedPreferences.edit { putBoolean("notification_enabled", isChecked) }
        if (isChecked) {
            view.showNotificationPermissionRequest()
        }
    }
}
