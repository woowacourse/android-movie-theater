package woowacourse.movie

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

object PermissionManager {
    fun requestNotificationPermission(
        activity: Activity,
        activityResultLauncher: ActivityResultLauncher<String>
    ) {
        if (isPermissionDenied(activity, POST_NOTIFICATIONS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(activity, POST_NOTIFICATIONS)) {
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.if_permission_is_denied_cant_use_notification_service),
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
                activityResultLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }

    fun isPermissionDenied(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
}
