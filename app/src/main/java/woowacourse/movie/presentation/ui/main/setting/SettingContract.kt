package woowacourse.movie.presentation.ui.main.setting

import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView

interface SettingContract {
    interface View : BaseView {
        fun showNotificationMode(mode: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadNotificationMode()

        fun changeNotificationMode(mode: Boolean)
    }
}
