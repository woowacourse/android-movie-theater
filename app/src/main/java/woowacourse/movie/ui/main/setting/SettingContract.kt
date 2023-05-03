package woowacourse.movie.ui.main.setting

interface SettingContract {
    interface View {
        fun setSwitchChecked(boolean: Boolean)
    }
    interface Presenter {
        fun getBoolean(notifications: String, boolean: Boolean)
        fun setBoolean(notifications: String, boolean: Boolean)
    }
}
