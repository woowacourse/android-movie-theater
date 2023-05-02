package woowacourse.movie.presentation.activities.main.fragments.setting.contract

interface SettingContract {
    interface View {
        val presenter: Presenter

        fun changePushSwitchState(newState: Boolean)
        fun checkPushPermission(): Boolean
        fun showPushPermissionDialog()
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchPushSwitchState()
        abstract fun updatePushAllow(newState: Boolean)
        abstract fun onPushSwitchClicked(newState: Boolean)
    }
}
