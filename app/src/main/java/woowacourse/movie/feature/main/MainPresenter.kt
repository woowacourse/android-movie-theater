package woowacourse.movie.feature.main

import woowacourse.movie.R

class MainPresenter(
    val view: MainContract.View
) : MainContract.Presenter {
    override fun permissionApproveResult(isGranted: Boolean) {
        if (isGranted) {
            view.showPermissionToast(R.string.alarm_notification_approve)
        } else {
            view.showPermissionToast(R.string.alarm_notification_reject)
        }
    }
}
