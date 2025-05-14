package woowacourse.movie.view.main

import woowacourse.movie.domain.datasource.PermissionDataSource

class MainPresenter(
    private val permissionDataSource: PermissionDataSource,
) : MainContract.Presenter {
    override fun updatePermission(isGranted: Boolean) {
        permissionDataSource.savePermission(isGranted)
    }
}
