package woowacourse.movie.feature.setting.presenter

import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.feature.setting.contract.SettingContract
import woowacourse.movie.feature.setting.view.SettingFragment.Companion.NOTIFICATION_SETTING_KEY

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreference: SharedPreferences,
) : SettingContract.Presenter {
    override fun getNotificationSetting() {
        val isNotificationEnabled = sharedPreference.getBoolean(NOTIFICATION_SETTING_KEY, true)
        view.updateNotificationSettingSwitch(isNotificationEnabled)
    }

    override fun saveNotificationSetting(isNotificationEnabled: Boolean) {
        sharedPreference.edit {
            putBoolean(NOTIFICATION_SETTING_KEY, isNotificationEnabled)
        }
    }
}
