package woowacourse.movie.presentation.ui.main

import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView

interface MainContract {
    interface View : BaseView

    interface Presenter : BasePresenter {
        fun changeNotificationMode(mode: Boolean)

        fun requestNotificationPermission(
            permissionText: String,
            action: Snackbar.() -> Snackbar,
        )
    }
}
