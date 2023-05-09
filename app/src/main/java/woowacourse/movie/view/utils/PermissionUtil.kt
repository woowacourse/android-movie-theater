package woowacourse.movie.view.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import woowacourse.movie.R

fun isPostingNotificationPermissionGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

fun showDialogToGetPostNotificationPermission(context: Context) {
    AlertDialog.Builder(context).apply {
        setTitle(context.getString(R.string.post_notification_permission_request_dialog_title))
        setMessage(context.getString(R.string.post_notification_permission_request_dialog_message))
        setPositiveButton(context.getString(R.string.ok)) { _, _ ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
    }
        .create()
        .show()
}
