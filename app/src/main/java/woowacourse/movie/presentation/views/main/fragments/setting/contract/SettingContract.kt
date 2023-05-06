package woowacourse.movie.presentation.views.main.fragments.setting.contract

interface SettingContract {
    interface View {
        val presenter: Presenter

        fun changePushSwitchState(newState: Boolean)
        fun checkPushPermission(): Boolean
        fun showPushPermissionDialog()
    }

    abstract class Presenter {
        private var view: View? = null

        fun attach(view: View) {
            this.view = view
        }

        fun detach() {
            this.view = null
        }

        fun requireView(): View = view ?: throw IllegalStateException("View is not attached")

        abstract fun getPushSwitchState(): Boolean
        abstract fun updatePushAllow(newState: Boolean)
        abstract fun onPushSwitchClicked(newState: Boolean)
    }
}
