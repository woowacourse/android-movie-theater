package woowacourse.movie.feature.main

class MainPresenter(
    val view: MainContract.View
) : MainContract.Presenter {
    override fun permissionApproveResult(isGranted: Boolean) {
        if (isGranted) {
            view.showPermissionApproveToast()
        } else {
            view.showPermissionRejectToast()
        }
    }

    override fun clickMovieTab() {
        view.showMovieList()
    }

    override fun clickReservationTab() {
        view.showReservationList()
    }

    override fun clickSettingTab() {
        view.showSetting()
    }
}
