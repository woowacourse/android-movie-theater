package woowacourse.movie.fragment.setting

interface SettingContract {

    interface View {
        var presenter: Presenter
        fun setSwitchStatus(setting: Boolean)
    }

    interface Presenter {
        fun initSwitchStatus(permission: Boolean)
        fun updateSwitchStatus(
            permission: Boolean,
            action: () -> Unit,
            isChecked: Boolean
        )
    }
}
