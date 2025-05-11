package woowacourse.movie.data.datasource

import woowacourse.movie.data.SharedPreferencesStore
import woowacourse.movie.domain.datasource.PermissionDataSource

class PermissionDataSourceImpl(private val prefs: SharedPreferencesStore) : PermissionDataSource {
    override fun savePermission(isGranted: Boolean) {
        prefs.saveNotificationPermissionResult(isGranted)
    }

    override fun isGranted(): Boolean {
        return prefs.notificationPermissionStatus()
    }
}
