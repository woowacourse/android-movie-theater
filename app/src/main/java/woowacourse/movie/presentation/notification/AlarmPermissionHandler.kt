package woowacourse.movie.presentation.notification

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import woowacourse.movie.R

class AlarmPermissionHandler(
    private val activity: Activity,
) {
    fun checkAndRequestPermission(onGranted: () -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            onGranted()
            return
        }

        val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (alarmManager.canScheduleExactAlarms()) {
            onGranted()
        } else {
            showPermissionDialog()
        }
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.permission_title))
            .setMessage(activity.getString(R.string.permission_description))
            .setPositiveButton(activity.getString(R.string.permission_confirm)) { _, _ ->
                openAlarmSettings()
            }
            .setNegativeButton(activity.getString(R.string.cancel), null)
            .setCancelable(false)
            .show()
    }

    private fun openAlarmSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent =
                Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = "package:${activity.packageName}".toUri()
                }
            activity.startActivity(intent)
        }
    }
}
