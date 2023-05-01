package woowacourse.movie.service

import android.Manifest
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

object PermissionManager {

    fun requestNotificationPermission(
        fragment: Fragment,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        requestPermission: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            if (!fragment.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermission()
            }
        }
    }

    fun getRequestPermissionLauncher(
        fragment: Fragment,
        onGranted: () -> Unit
    ): ActivityResultLauncher<String> {
        return fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                onGranted()
            }
        }
    }
}
