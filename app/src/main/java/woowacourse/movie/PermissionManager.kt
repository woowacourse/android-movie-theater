package woowacourse.movie

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

class PermissionManager(
    private val activity: Activity,
    private val activityResultLauncher: ActivityResultLauncher<String>
) {
    fun requestPermission(
        permission: String,
        ifDeniedDescription: String,
    ) {
        if (isPermissionDenied(permission)) {
            if (shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(
                    activity,
                    ifDeniedDescription,
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            activityResultLauncher.launch(permission)
        }
    }

    fun isPermissionDenied(permission: String) =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED

    fun isPermissionDeniedForever(permission: String) =
        !shouldShowRequestPermissionRationale(activity, permission)
}
