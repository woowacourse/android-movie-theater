package woowacourse.movie.view.main

import android.content.Context
import woowacourse.movie.data.PermissionSharedPreferences
import woowacourse.movie.data.datasource.PermissionDataSourceImpl

class MainPresenterFactory() {
    fun initialize(context: Context): MainContract.Presenter {
        val permissionSharedPreferences = PermissionSharedPreferences(context)
        val permissionDataSource = PermissionDataSourceImpl(permissionSharedPreferences)
        return MainPresenter(permissionDataSource)
    }
}
