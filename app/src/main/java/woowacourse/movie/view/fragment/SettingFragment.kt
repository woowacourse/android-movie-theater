package woowacourse.movie.view.fragment

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.contract.SettingContract
import woowacourse.movie.presenter.SettingPresenter
import woowacourse.movie.system.PermissionLauncher
import woowacourse.movie.system.PermissionLauncher.makePermissionResultLauncher
import woowacourse.movie.system.PermissionLauncher.requestPermission
import woowacourse.movie.system.Setting
import woowacourse.movie.system.SharedSetting

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {
    override val presenter: SettingContract.Presenter = SettingPresenter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initFragment()
    }

    override fun makeSettingSwitch() {
        val view = view ?: return
        val setting: Setting = SharedSetting(view.context)
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val permissionResultLauncher: ActivityResultLauncher<String> =
            makePermissionResultLauncher {
                onNotificationAddResult(switch, setting, it)
            }

        switch.isChecked = setting.getValue(SETTING_NOTIFICATION) && PermissionLauncher.isGranted(
            requireContext(), permission
        )

        switch.setOnCheckedChangeListener { _, isChecked ->
            presenter.toggleNotificationSetting(
                permissionResultLauncher,
                setting,
                permission,
                isChecked
            )
        }
    }

    private fun onNotificationAddResult(
        switch: SwitchCompat,
        setting: Setting,
        isGranted: Boolean
    ) {
        switch.isChecked = isGranted
        setting.setValue(SETTING_NOTIFICATION, isGranted)
    }

    override fun onNotificationSwitchCheckedChangeListener(
        permissionResultLauncher: ActivityResultLauncher<String>,
        setting: Setting,
        permission: String,
        isChecked: Boolean
    ) {
        if (isChecked) {
            if (PermissionLauncher.isGranted(requireContext(), permission))
                setting.setValue(SETTING_NOTIFICATION, true)
            else
                requestPermission(permission, permissionResultLauncher)
            return
        }
        setting.setValue(SETTING_NOTIFICATION, false)
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
