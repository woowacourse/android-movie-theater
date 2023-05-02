package woowacourse.movie.view.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.system.PermissionLauncher
import woowacourse.movie.system.PermissionLauncher.makePermissionResultLauncher
import woowacourse.movie.system.PermissionLauncher.requestPermission
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
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val permissionResultLauncher: ActivityResultLauncher<String> =
            makePermissionResultLauncher {
                onNotificationAddResult(switch, setting, it)
            }

        switch.isChecked = setting.getValue(SETTING_NOTIFICATION) && PermissionLauncher.isGranted(
            requireContext(), permission
        )

        switch.setOnCheckedChangeListener { _, isChecked ->
            onNotificationSwitchCheckedChangeListener(
                permissionResultLauncher, setting, permission, isChecked
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

    private fun onNotificationSwitchCheckedChangeListener(
        permissionResultLauncher: ActivityResultLauncher<String>,
        setting: Setting,
        permission: String,
        isChecked: Boolean
    ) {
        if (isChecked && !PermissionLauncher.isGranted(requireContext(), permission)) {
            requestPermission(permission, permissionResultLauncher)
            return
        }
        setting.setValue(SETTING_NOTIFICATION, false)
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
