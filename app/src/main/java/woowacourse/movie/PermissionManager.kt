package woowacourse.movie

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

object PermissionManager {
    fun requestPermission(
        permission: String,
        activity: Activity,
        activityResultLauncher: ActivityResultLauncher<String>,
        ifDeniedDescription: String,
    ) {
        if (isPermissionDenied(activity, permission)) {
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

    fun isPermissionDenied(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
}
