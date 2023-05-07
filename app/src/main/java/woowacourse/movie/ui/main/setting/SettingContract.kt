package woowacourse.movie.ui.main.setting

import woowacourse.movie.ui.base.BaseContract

interface SettingContract {
    interface View {
        fun setSwitchChecked(boolean: Boolean)
    }
    interface Presenter : BaseContract.Presenter {
        fun setUpSwitch(notifications: String, boolean: Boolean)
        fun updateSwitch(notifications: String, boolean: Boolean)
    }
}
