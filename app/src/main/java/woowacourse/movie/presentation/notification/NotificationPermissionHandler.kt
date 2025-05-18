package woowacourse.movie.presentation.notification

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import woowacourse.movie.R

class NotificationPermissionHandler(
    private val activity: Activity,
    private val launcher: ActivityResultLauncher<String>,
) {
    fun checkAndRequest() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> {}
            activity.shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(activity, R.string.permission_denied, Toast.LENGTH_SHORT).show()
            }
            else -> {
                launcher.launch(permission)
            }
        }
    }
}
