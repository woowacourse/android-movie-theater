package woowacourse.movie.view.setting

import woowacourse.movie.domain.datasource.PermissionDataSource

class SettingPresenter(
    private val view: SettingContract.View,
    private val permissionDataSource: PermissionDataSource,
) : SettingContract.Presenter {
    override fun synchronizePermission(isGranted: Boolean) {
        val currentPermission = permissionDataSource.isGranted()
        if (currentPermission != isGranted) {
            permissionDataSource.savePermission(isGranted)
        }
    }

    override fun setPreferences(isGranted: Boolean) {
        permissionDataSource.savePermission(isGranted)
        view.showNotificationPermission(isGranted)
    }
}
