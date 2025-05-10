package woowacourse.movie.view.setting

import android.util.Log

class SettingPresenter(
    private val view: SettingContract.View,
    private val manager: SettingStorageManager,
) : SettingContract.Presenter {
    override fun loadSettings() {
        val notificationEnabled: Boolean = manager.loadNotificationSetting()
        Log.d("temp", "$notificationEnabled")
        view.showNotificationSetting(notificationEnabled)
    }

    override fun toggleNotificationSetting() {
        val notificationEnabled: Boolean = !manager.loadNotificationSetting()
        manager.updateNotificationSetting(notificationEnabled)
        view.showNotificationSetting(notificationEnabled)
        Log.d("temp", "$notificationEnabled")
    }
}
