package woowacourse.movie.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher

object PermissionManager {
    fun requestPermission(
        activity: Activity,
        permission: String,
        showRationale: () -> Unit,
        requestPermissionLauncher: ActivityResultLauncher<String>
    ) {
        if (!checkGrantedPermission(activity, permission)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    // 권한 요청 거부한 경우
                    showRationale()
                } else {
                    requestPermissionLauncher.launch(permission)
                }
            }
        }
    }

    fun checkGrantedPermission(context: Context, permission: String): Boolean =
        context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}
