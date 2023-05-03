package woowacourse.movie.presentation.allowance

import android.content.Context
import woowacourse.movie.data.settings.SettingsPreference

object NotificationAllowance {

    private const val prefKey = "Notification"
    fun isNotifiable(context: Context): Boolean =
        SettingsPreference.getInstance(prefKey, context).isAvailable

    fun setNotifiable(context: Context, notifyState: Boolean) {
        SettingsPreference.getInstance(prefKey, context).isAvailable = notifyState
    }
}
