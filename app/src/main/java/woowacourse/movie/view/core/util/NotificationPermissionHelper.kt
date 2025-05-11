package woowacourse.movie.view.permission

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import woowacourse.movie.R
import woowacourse.movie.data.SharedPreferencesStore
import woowacourse.movie.view.core.ext.checkNotificationPermission

/**
 *
 * @param activity 권한 요청을 실행할 Activity
 * @param caller registerForActivityResult를 사용할 수 있는 Activity/Fragment
 * @param prefsManager 사용자의 권한 응답 여부를 저장할 SharedPreferences
 */
class NotificationPermissionHelper(
    private val activity: Activity,
    caller: ActivityResultCaller,
    private val prefsManager: SharedPreferencesStore,
) {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher =
                caller.registerForActivityResult(
                    ActivityResultContracts.RequestPermission(),
                ) { isGranted ->
                    prefsManager.saveNotificationPermissionResult(isGranted)
                    if (!isGranted && !activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                        showSettingsDialog()
                    }
                }
        }
    }

    /**
     * 알림 권한이 필요한 경우 사용자에게 요청
     * 권한이 없고 Rationale이 필요한 경우 설명 다이얼로그 출력
     *  Rationale :
     *  사용자가 이전에 권한을 거부했을 때 앱이 "왜 이 권한이 필요한지" 설명
     * 그렇지 않으면 직접 권한을 요청
     */
    fun requestPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        when {
            activity.checkNotificationPermission() -> {
                if (!prefsManager.notificationPermissionStatus()) {
                    prefsManager.saveNotificationPermissionResult(true)
                }
            }

            activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                showRationaleDialog()
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * 사용자에게 권한 요청 이유를 설명하고, 권한 요청을 유도하는 다이얼로그를 표시
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showRationaleDialog() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.permission_notification_title)
            .setMessage(R.string.permission_notification_message)
            .setPositiveButton(R.string.permission_notification_allow) { _, _ ->
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(R.string.permission_notification_deny, null)
            .show()
    }

    /**
     * 사용자가 권한을 영구적으로 거부한 경우 설정 화면으로 이동하도록 유도하는 다이얼로그 표시
     */
    private fun showSettingsDialog() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.permission_notification_settings_title)
            .setMessage(R.string.permission_notification_settings_message)
            .setPositiveButton(R.string.permission_notification_settings_move_to_setting) { _, _ ->
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", activity.packageName, null)
                    }
                activity.startActivity(intent)
            }
            .setNegativeButton(R.string.permission_notification_settings_cancel, null)
            .show()
    }
}
