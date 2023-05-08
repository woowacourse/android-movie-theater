package woowacourse.movie.presentation.ui.main.fragments.setting.contract

import woowacourse.movie.presentation.base.BaseContract

interface SettingContract {
    interface View : BaseContract.View {
        fun changePushSwitchState(newState: Boolean)
        fun checkPushPermission(): Boolean
        fun showPushPermissionDialog()
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun getPushSwitchState(): Boolean
        abstract fun updatePushAllow(newState: Boolean)
        abstract fun changePushState(newState: Boolean)
    }
}
