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

fun hasPermission(context: Context, permission: String): Boolean =
    ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
