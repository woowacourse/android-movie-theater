package woowacourse.movie.presenter

import androidx.activity.result.ActivityResultLauncher
import woowacourse.movie.contract.SettingContract
import woowacourse.movie.system.Setting

class SettingPresenter(override val view: SettingContract.View) : SettingContract.Presenter {
    override fun initFragment() {
        view.makeSettingSwitch()
    }

    override fun toggleNotificationSetting(
        permissionResultLauncher: ActivityResultLauncher<String>,
        setting: Setting,
        permission: String,
        isChecked: Boolean
    ) {
        view.onNotificationSwitchCheckedChangeListener(
            permissionResultLauncher, setting, permission, isChecked
        )
    }
}
