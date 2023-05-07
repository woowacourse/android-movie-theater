package woowacourse.movie.ui.main.setting

import woowacourse.movie.ui.BaseContract

interface SettingContract {
    interface View {
        fun setSwitchChecked(boolean: Boolean)
    }
    interface Presenter : BaseContract.Presenter {
        fun getBoolean(notifications: String, boolean: Boolean)
        fun setBoolean(notifications: String, boolean: Boolean)
    }
}
