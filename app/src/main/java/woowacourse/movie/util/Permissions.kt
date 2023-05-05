package woowacourse.movie.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

fun requestPermission(
    activity: Activity,
    permission: String,
    requestPermission: (String) -> Unit
) {
    if (hasPermission(activity, permission)) return

    if (!shouldShowRequestPermissionRationale(activity, permission)) {
        requestPermission(permission)
    }
}

// fun Context.hasPermission(permission: String): Boolean =
//    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/*
    SettingFragment에서 사용중인 메서드
    requireContext를 사용할 때에는 원하는대로 작동하지 않음 (현재 앱에 대한 permission의 여부에 따른 변화 적용x)
    requireActivity를 사용하였을 때에는 원하는대로 작동.
    ...?
 */
fun hasPermission(context: Context, permission: String): Boolean =
    ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
