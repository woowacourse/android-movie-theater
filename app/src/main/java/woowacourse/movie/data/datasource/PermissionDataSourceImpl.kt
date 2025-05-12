package woowacourse.movie.data.datasource

import woowacourse.movie.data.PermissionSharedPreferences
import woowacourse.movie.domain.datasource.PermissionDataSource

class PermissionDataSourceImpl(private val prefs: PermissionSharedPreferences) : PermissionDataSource {
    override fun savePermission(isGranted: Boolean) {
        prefs.saveNotificationPermissionResult(isGranted)
    }

    override fun isGranted(): Boolean {
        return prefs.notificationPermission()
    }
}
