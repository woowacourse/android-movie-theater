package woowacourse.movie.system

import androidx.activity.ComponentActivity

object PermissionLauncherProvider {
    val permissionLaunchers: MutableMap<ComponentActivity, PermissionLauncher> = mutableMapOf()
}
