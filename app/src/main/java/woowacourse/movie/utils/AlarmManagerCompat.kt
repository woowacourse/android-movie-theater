package woowacourse.movie.utils

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.net.toUri
import woowacourse.movie.R
import woowacourse.movie.providers.StorageProvider

object AlarmManagerCompat {
    fun setExact(
        context: Context,
        triggerAtMillis: Long,
        pendingIntent: PendingIntent,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            requestScheduleExactPermission(context)
            return
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }

    fun requestScheduleExactPermission(context: Context) {
        val intent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = "package:${context.packageName}".toUri()
                }
            } else {
                TODO("VERSION.SDK_INT < S")
            }

        showNotificationDialog(context, intent)
    }

    fun hasExactAlarmPermission(context: Context): Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun showNotificationDialog(
        context: Context,
        intent: Intent,
    ) {
        AlertDialog
            .Builder(context)
            .setTitle(context.getString(R.string.dialog_alarm_title))
            .setMessage(context.getString(R.string.dialog_alarm_message))
            .setPositiveButton(context.getString(R.string.dialog_alarm_positive_btn)) { _, _ ->
                context.startActivity(intent)
                showAlarmAllowedMessage(context)
                StorageProvider.setFirstExactAlarmPermissionState(false)
            }
            .setNegativeButton(context.getString(R.string.dialog_alarm_negative_btn)) { dialog, _ ->
                dialog.dismiss()
                showAlarmNotAllowedMessage(context)
                StorageProvider.setFirstExactAlarmPermissionState(false)
            }.setCancelable(false)
            .show()
    }

    private fun showAlarmAllowedMessage(context: Context) {
        Toast.makeText(
            context,
            context.getString(R.string.dialog_select_positive_message),
            Toast.LENGTH_SHORT,
        ).show()
    }

    private fun showAlarmNotAllowedMessage(context: Context) {
        Toast.makeText(
            context,
            context.getString(R.string.dialog_select_negative_message),
            Toast.LENGTH_SHORT,
        ).show()
    }
}
