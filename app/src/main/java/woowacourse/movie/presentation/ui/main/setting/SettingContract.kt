package woowacourse.movie.presentation.ui.main.setting

import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView

interface SettingContract {
    interface View : BaseView {
        fun showNotificationMode(mode: Boolean)

        fun checkNotificationPermissions(mode: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadNotificationMode()

        fun changeNotificationMode(mode: Boolean)

        fun saveNotificationMode(mode: Boolean)

        fun requestNotificationPermission(
            permissionText: String,
            action: Snackbar.() -> Snackbar,
        )
    }
}
