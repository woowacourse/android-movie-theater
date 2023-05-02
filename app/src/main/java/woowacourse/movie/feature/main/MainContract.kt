package woowacourse.movie.feature.main

interface MainContract {
    interface View {
        fun showPermissionToast(messageId: Int)
    }

    interface Presenter {
        fun permissionApproveResult(isGranted: Boolean)
    }
}
