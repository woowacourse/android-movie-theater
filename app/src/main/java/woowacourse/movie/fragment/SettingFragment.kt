package woowacourse.movie.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.system.PermissionLauncher
import woowacourse.movie.system.PermissionLauncherProvider
import woowacourse.movie.system.Setting
import woowacourse.movie.system.SharedSetting

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        makeNotificationSwitchCompat(view)
        return view
    }

    private fun makeNotificationSwitchCompat(view: View) {
        val setting: Setting = SharedSetting(view.context)
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        val permissionLauncher = PermissionLauncherProvider.permissionLaunchers[requireActivity()]
        val permission = Manifest.permission.POST_NOTIFICATIONS

        switch.isChecked =
            setting.getSettingValue(SETTING_NOTIFICATION) &&
            (permissionLauncher?.isGranted(permission) ?: true)

        permissionLauncher?.addResult(permission) {
            onNotificationAddResult(switch, setting, it)
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            onNotificationSwitchCheckedChangeListener(
                setting, permissionLauncher, permission, isChecked
            )
        }
    }

    private fun onNotificationAddResult(
        switch: SwitchCompat,
        setting: Setting,
        isGranted: Boolean
    ) {
        switch.isChecked = isGranted
        setting.setSettingValue(SETTING_NOTIFICATION, isGranted)
    }

    private fun onNotificationSwitchCheckedChangeListener(
        setting: Setting,
        permissionLauncher: PermissionLauncher?,
        permission: String,
        isChecked: Boolean
    ) {
        if (isChecked && permissionLauncher?.isGranted(permission) == false) {
            permissionLauncher.requestNotificationPermission(permission)
            return
        }
        setting.setSettingValue(SETTING_NOTIFICATION, false)
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
