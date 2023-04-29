package woowacourse.movie.service

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.view.error.FragmentError.finishWithNullViewError

object Permission {

    fun requestNotificationPermission(
        fragment: Fragment,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        requestPermission: (View) -> Unit
    ) {
        val view: View = fragment.view ?: return fragment.finishWithNullViewError()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            if (!fragment.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermission(view)
            }
        }
    }

    fun getRequestPermissionLauncher(
        fragment: Fragment,
        onGranted: (View?) -> Unit
    ): ActivityResultLauncher<String> {
        return fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                onGranted(fragment.view)
            }
        }
    }

    fun checkNotificationPermission(view: View): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                view.context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else false
    }
}
