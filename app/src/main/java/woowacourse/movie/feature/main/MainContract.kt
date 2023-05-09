package woowacourse.movie.feature.main

interface MainContract {
    interface View {
        fun showPermissionApproveMessage()
        fun showPermissionRejectMessage()
        fun showMoviePage()
        fun showReservationPage()
        fun showSettingPage()
    }

    interface Presenter {
        fun permissionApproveResult(isGranted: Boolean)
        fun clickMovieTab()
        fun clickReservationTab()
        fun clickSettingTab()
    }
}
