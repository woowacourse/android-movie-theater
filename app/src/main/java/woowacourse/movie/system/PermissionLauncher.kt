package woowacourse.movie.system

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionLauncher(private val activity: ComponentActivity) {

    fun interface ActivityResult {
        fun result(isGranted: Boolean)
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            results.forEach { result -> result.value.result(it) }
        }

    private val results: MutableMap<String, ActivityResult> = mutableMapOf()

    fun addResult(permission: String, result: ActivityResult) {
        results[permission] = result
    }

    fun requestNotificationPermission(permission: String) {
        if (!isGranted(permission) && activity.shouldShowRequestPermissionRationale(permission)) {
            requestPermissionLauncher.launch(permission)
        }
    }

    fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}
