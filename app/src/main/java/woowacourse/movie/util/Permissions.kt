package woowacourse.movie.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Activity.hasPermissions(permissions: Array<String>): Boolean {
    permissions.forEach { permission ->
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun Activity.requestPermissions(permissions: Array<String>, requestPermission: (String) -> Unit) {
    permissions.forEach { permission ->
        if (this.hasPermissions(arrayOf(permission))) { return }

        if (!shouldShowRequestPermissionRationale(permission)) {
            requestPermission(permission)
        }
    }
}
