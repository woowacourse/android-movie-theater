package woowacourse.movie.view.setting

import android.content.Context
import woowacourse.movie.data.SharedPreferencesStore

class SettingPresenter(
    private val view: SettingContract.View,
    private val prefs: SharedPreferencesStore,
) : SettingContract.Presenter {
    override fun synchronizePermission(isGranted: Boolean) {
        val currentPermission = prefs.notificationPermissionStatus()
        if (currentPermission != isGranted) {
            prefs.saveNotificationPermissionResult(isGranted)
        }
    }

    override fun setPreferences(isGranted: Boolean) {
        prefs.saveNotificationPermissionResult(isGranted)
        view.showNotificationPermission(isGranted)
    }

    companion object {
        fun initialize(
            view: SettingContract.View,
            context: Context,
        ): SettingContract.Presenter {
            val prefsManager = SharedPreferencesStore(context)
            return SettingPresenter(view, prefsManager)
        }
    }
}
