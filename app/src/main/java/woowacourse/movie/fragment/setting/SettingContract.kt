package woowacourse.movie.fragment.setting

interface SettingContract {

    interface View {
        var presenter: Presenter
        fun setSwitchStatus(setting: Boolean)
        fun requestNotificationPermission()
    }

    interface Presenter {
        fun loadSwitchStatus(permission: Boolean)
        fun updateSwitchStatus(
            permission: Boolean,
            isChecked: Boolean,
        )
    }
}
