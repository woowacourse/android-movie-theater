package woowacourse.movie.presentation.extensions

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Build.VERSION
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import woowacourse.movie.R

fun Context.checkPermissionCompat(permission: String): Boolean =
    if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        checkPermission(permission)
    } else {
        true
    }

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

fun AlertDialog.Builder.title(text: String) {
    this.setTitle(text)
}

fun AlertDialog.Builder.message(text: String) {
    this.setMessage(text)
}

fun AlertDialog.Builder.positiveButton(
    text: String = context.getString(R.string.yes),
    onClick: (dialogInterface: DialogInterface) -> Unit = {},
) {
    this.setPositiveButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}

fun AlertDialog.Builder.negativeButton(
    text: String = context.getString(R.string.no),
    onClick: (dialogInterface: DialogInterface) -> Unit = {},
) {
    this.setNegativeButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}
