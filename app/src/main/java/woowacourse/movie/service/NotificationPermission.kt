package woowacourse.movie.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R

object NotificationPermission {

    fun getPermissionLauncher(
        fragment: Fragment,
        onNotGranted: () -> Unit,
    ): ActivityResultLauncher<String> {
        return fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                onNotGranted()
                Toast.makeText(
                    fragment.context,
                    fragment.getString(R.string.if_permission_is_denied_cant_use_notification_service),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun Context.checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else false
    }

    fun requestNotificationPermission(
        requestPermissionLauncher: ActivityResultLauncher<String>,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
