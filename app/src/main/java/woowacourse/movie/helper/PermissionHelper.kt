package woowacourse.movie.helper

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionHelper {
    fun checkPermission(
        minSdk: Int,
        permission: String,
        context: Context,
        launcher: ActivityResultLauncher<String>
    ) {
        if (Build.VERSION.SDK_INT >= minSdk) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                launcher.launch(permission)
            }
        }
    }
}
