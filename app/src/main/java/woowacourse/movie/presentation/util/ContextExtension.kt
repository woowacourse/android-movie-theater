package woowacourse.movie.presentation.util

import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat

fun Context.checkPermissionTiramisu(permission: String): Boolean =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        checkPermission(permission)
    } else {
        true
    }

fun Context.checkPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) ==
        android.content.pm.PackageManager.PERMISSION_GRANTED
