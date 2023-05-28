package woowacourse.movie.util.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class NotificationPermissionChecker private constructor(
    private val context: Context
) : PermissionChecker {

    override val hasPermission: Boolean
        get() = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private var instance: PermissionChecker? = null

        fun getInstance(context: Context): PermissionChecker {
            return instance ?: synchronized(this) {
                instance ?: NotificationPermissionChecker(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }
}
