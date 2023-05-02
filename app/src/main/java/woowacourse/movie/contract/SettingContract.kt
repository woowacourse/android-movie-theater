package woowacourse.movie.contract

import androidx.activity.result.ActivityResultLauncher
import woowacourse.movie.system.Setting

interface SettingContract {
    interface View {
        val presenter: Presenter
        fun makeSettingSwitch()
        fun onNotificationSwitchCheckedChangeListener(
            permissionResultLauncher: ActivityResultLauncher<String>,
            setting: Setting,
            permission: String,
            isChecked: Boolean
        )
    }

    interface Presenter {
        val view: View
        fun initFragment()
        fun toggleNotificationSetting(
            permissionResultLauncher: ActivityResultLauncher<String>,
            setting: Setting,
            permission: String,
            isChecked: Boolean
        )
    }
}
