package woowacourse.movie.view.setting

import android.content.Context
import woowacourse.movie.data.SharedPreferencesStore
import woowacourse.movie.data.datasource.PermissionDataSourceImpl
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

    companion object {
        fun initialize(
            view: SettingContract.View,
            context: Context,
        ): SettingContract.Presenter {
            val prefsManager = SharedPreferencesStore(context)
            val permissionDataSource = PermissionDataSourceImpl(prefsManager)
            return SettingPresenter(view, permissionDataSource)
        }
    }
}
