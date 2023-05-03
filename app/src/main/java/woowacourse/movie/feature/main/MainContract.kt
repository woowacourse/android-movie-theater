package woowacourse.movie.feature.main

interface MainContract {
    interface View {
        fun showPermissionApproveToast()
        fun showPermissionRejectToast()
        fun showMovieList()
        fun showReservationList()
        fun showSetting()
    }

    interface Presenter {
        fun permissionApproveResult(isGranted: Boolean)
        fun clickMovieTab()
        fun clickReservationTab()
        fun clickSettingTab()
    }
}
