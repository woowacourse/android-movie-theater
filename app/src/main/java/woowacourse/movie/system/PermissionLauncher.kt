package woowacourse.movie.system

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.requestPermission(
    permission: String,
    permissionResultLauncher: ActivityResultLauncher<String>
) {
    if (isGranted(requireContext(), permission)) return
    if (!shouldShowRequestPermissionRationale(permission)) return
    permissionResultLauncher.launch(permission)
}

fun Fragment.makePermissionResultLauncher(callback: (Boolean) -> Unit): ActivityResultLauncher<String> {
    return registerForActivityResult(
        ActivityResultContracts.RequestPermission(), callback
    )
}

fun isGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context, permission
    ) == PackageManager.PERMISSION_GRANTED
}
