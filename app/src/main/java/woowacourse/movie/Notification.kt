package woowacourse.movie

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.AlertDialog

class Notification() {
    companion object {
        private const val ALREADY_ALLOW = "이미 권한 있음"
        private const val NOT_ALLOW = "권한 요청이 필요 없음"

        fun askNotificationPermission(
            context: Context,
            launcher: ActivityResultLauncher<String>
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    showNotificationPermissionDialog(context, launcher)
                } else {
                    Toast.makeText(context, ALREADY_ALLOW, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, NOT_ALLOW, Toast.LENGTH_SHORT).show()
            }
        }

        fun showNotificationPermissionDialog(
            context: Context,
            launcher: ActivityResultLauncher<String>,
        ) {
            AlertDialog.Builder(context)
                .setTitle("알림 허용")
                .setMessage("Movie에서 알림을 보내도록 허용하시겠습니까?")
                .setPositiveButton("허용") { _, _ ->
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    SharedPreferences.saveData(context, true)
                }
                .setNegativeButton("허용 안함") { _, _ ->
                    SharedPreferences.saveData(context, false)
                }
                .show()
        }
    }
}
