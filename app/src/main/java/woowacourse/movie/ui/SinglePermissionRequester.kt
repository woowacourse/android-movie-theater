package woowacourse.movie.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

object SinglePermissionRequester {
    internal const val NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS

    fun requestPermission(
        activity: Activity,
        permission: String,
        versionCondition: Int,
        grantedAction: () -> Unit,
        deniedAction: () -> Unit
    ) {
        if (checkDeniedPermission(activity.applicationContext, permission)) {
            actionToCheckBuildVersion(
                versionCondition,
                { actionToRequestPermission(activity, permission, grantedAction, deniedAction) },
                { grantedAction() }
            )
        }
    }

    fun checkDeniedPermission(context: Context, permission: String): Boolean =
        context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED

    private fun actionToCheckBuildVersion(
        versionCondition: Int,
        actionToRequestPermission: () -> Unit,
        requestAlwaysPermission: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= versionCondition) {
            actionToRequestPermission()
        } else {
            requestAlwaysPermission()
        }
    }

    private fun actionToRequestPermission(
        activity: Activity,
        permission: String,
        grantedAction: () -> Unit,
        deniedAction: () -> Unit
    ) {
        if (!activity.shouldShowRequestPermissionRationale(permission))
            grantedAction()
        else
            deniedAction()
    }
}
