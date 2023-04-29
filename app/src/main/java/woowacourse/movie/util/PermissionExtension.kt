package woowacourse.movie.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Activity.requestRequiredPermissions(
    permissions: List<String>,
    requestPermission: (String) -> Unit
) {
    permissions.forEach { permission ->
        if (this.isGranted(permission)) return@forEach
        requestPermission(permission)
    }
}

fun Activity.isGranted(permissions: String): Boolean {
    if (ContextCompat.checkSelfPermission(
            this,
            permissions
        ) != PackageManager.PERMISSION_GRANTED
    ) return false
    return true
}
