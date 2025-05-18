package woowacourse.movie.view.setting

import android.content.Context
import woowacourse.movie.data.PermissionSharedPreferences
import woowacourse.movie.data.datasource.PermissionDataSourceImpl

class SettingPresenterFactory {
    fun initialize(
        view: SettingContract.View,
        context: Context,
    ): SettingContract.Presenter {
        val prefsManager = PermissionSharedPreferences(context)
        val permissionDataSource = PermissionDataSourceImpl(prefsManager)
        return SettingPresenter(view, permissionDataSource)
    }
}
