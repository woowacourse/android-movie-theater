package woowacourse.movie.feature.setting.presenter

import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.feature.setting.contract.SettingContract

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreferences: SharedPreferences,
) : SettingContract.Presenter {
    override fun loadNotificationSettings() {
        val isNotificationEnabled = sharedPreferences.getBoolean("NOTIFICATION_ENABLED", false)
        view.setNotificationSwitchChecked(isNotificationEnabled)
    }

    override fun toggleNotificationSwitch(isChecked: Boolean) {
        sharedPreferences.edit { putBoolean("NOTIFICATION_ENABLED", isChecked) }
        view.setNotificationSwitchChecked(isChecked)
    }
}
