package woowacourse.movie.view.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import woowacourse.movie.R
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
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
    private val view: View,
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
                        showPermissionSnackBar()
                    }
                }
        }
    }

    /**
     * 알림 권한이 필요한 경우 사용자에게 요청
     * 권한이 없고 Rationale이 필요한 경우 설명 다이얼로그 출력
     *  Rationale :
     * 사용자가 이전에 권한을 거부했을 때 앱이 "왜 이 권한이 필요한지" 설명
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
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    fun showPermissionSnackBar() {
        Snackbar.make(view, R.string.permission_notification_settings_message, Snackbar.LENGTH_SHORT)
            .setAction(R.string.text_complete) {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", activity.packageName, null)
                    }
                activity.startActivity(intent)
            }.show()
    }
}
