package woowacourse.movie.util.permission

import android.content.Context

interface PermissionChecker {
    val hasPermission: Boolean

    companion object {
        const val NOTIFICATION = "notification"

        fun getInstance(context: Context, option: String): PermissionChecker {
            return when (option) {
                NOTIFICATION -> NotificationPermissionChecker.getInstance(context)
                else -> throw IllegalArgumentException()
            }
        }
    }
}
