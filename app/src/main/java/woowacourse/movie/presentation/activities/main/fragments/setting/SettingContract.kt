package woowacourse.movie.presentation.activities.main.fragments.setting

interface SettingContract {
    interface View {
        val presenter: Presenter

        fun showPushPermissionDialog()
    }

    interface Presenter {
        fun setPushAllow(value: Boolean)
        fun getPushAllow(defaultValue: Boolean): Boolean
        fun onSwitchChanged(isPermittedPushPermission: Boolean, isAllowed: Boolean)
    }
}
