package woowacourse.movie.presentation.extensions

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

fun Context.checkPermissions(vararg permissions: String): Boolean =
    permissions.all { checkPermission(it) }

fun Context.checkPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) ==
        android.content.pm.PackageManager.PERMISSION_GRANTED

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.createAlertDialog(
    isCancelable: Boolean = true,
    builder: AlertDialog.Builder.() -> Unit,
): AlertDialog =
    AlertDialog.Builder(this).apply {
        setCancelable(isCancelable)
        builder()
    }.create()
