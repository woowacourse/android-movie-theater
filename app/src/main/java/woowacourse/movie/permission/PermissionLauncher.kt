package woowacourse.movie.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

object PermissionLauncher {

    fun requestPermission(
        context: Context,
        launcher: ActivityResultLauncher<String>,
        permission: String
    ) {
        if (!isAllowed(context, permission) && isPermissionNeededVersion()) {
            launcher.launch(permission)
        }
    }

    fun <T : ActivityResultCaller> T.getPermissionLauncher(
        deniedCase: () -> Unit,
        allowedCase: () -> Unit
    ): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                allowedCase()
            } else {
                deniedCase()
            }
        }
    }

    private fun isAllowed(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    private fun isPermissionNeededVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
