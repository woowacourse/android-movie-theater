package woowacourse.movie.view.main

import android.content.Context
import woowacourse.movie.data.PermissionSharedPreferences
import woowacourse.movie.data.datasource.PermissionDataSourceImpl
import woowacourse.movie.domain.datasource.PermissionDataSource

class MainPresenter(
    private val permissionDataSource: PermissionDataSource,
) : MainContract.Presenter {
    override fun updatePermission(isGranted: Boolean) {
        permissionDataSource.savePermission(isGranted)
    }

    companion object {
        fun initialize(context: Context): MainContract.Presenter {
            val permissionSharedPreferences = PermissionSharedPreferences(context)
            val permissionDataSource = PermissionDataSourceImpl(permissionSharedPreferences)

            return MainPresenter(permissionDataSource)
        }
    }
}
