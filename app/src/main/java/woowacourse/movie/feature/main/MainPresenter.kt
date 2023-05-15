package woowacourse.movie.feature.main

class MainPresenter(
    val view: MainContract.View
) : MainContract.Presenter {
    override fun permissionApproveResult(isGranted: Boolean) {
        if (isGranted) {
            view.showPermissionApproveMessage()
        } else {
            view.showPermissionRejectMessage()
        }
    }

    override fun clickMovieTab() {
        view.showMoviePage()
    }

    override fun clickReservationTab() {
        view.showReservationPage()
    }

    override fun clickSettingTab() {
        view.showSettingPage()
    }
}
