package woowacourse.movie.ui.setting

import woowacourse.movie.ui.HandleError

interface MovieSettingContract {
    interface View : HandleError {
        fun setInitialSetting(isEnabled: Boolean)

        fun updateReceiveNotificationStatus(isEnabled: Boolean)
    }

    interface Presenter {
        fun loadInitialSetting()

        fun updateNotificationSelection(isChecked: Boolean)
    }
}
