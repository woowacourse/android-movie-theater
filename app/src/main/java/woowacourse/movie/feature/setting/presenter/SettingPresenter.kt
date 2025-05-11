package woowacourse.movie.feature.setting.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.feature.setting.MyReceiver
import woowacourse.movie.feature.setting.contract.SettingContract

class SettingPresenter(
    private val context: Context,
    private val view: SettingContract.View,
    private val sharedPreferences: SharedPreferences,
) : SettingContract.Presenter {
    override fun loadNotificationSettings() {
        val isNotificationEnabled = sharedPreferences.getBoolean("NOTIFICATION_ENABLED", false)
        view.setNotificationSwitchChecked(isNotificationEnabled)
    }

    override fun toggleNotificationSwitch(isChecked: Boolean) {
        sharedPreferences.edit { putBoolean("NOTIFICATION_ENABLED", isChecked) }
        if (isChecked) {
            view.showNotificationPermissionRequest()
        } else {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, MyReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
            alarmManager.cancel(pendingIntent)
        }
    }
}
