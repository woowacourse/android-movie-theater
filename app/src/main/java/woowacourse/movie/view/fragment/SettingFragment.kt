package woowacourse.movie.view.fragment

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.contract.SettingContract
import woowacourse.movie.data.dataSource.SharedSetting
import woowacourse.movie.presenter.SettingPresenter
import woowacourse.movie.system.PermissionLauncher
import woowacourse.movie.system.PermissionLauncher.makePermissionResultLauncher
import woowacourse.movie.system.PermissionLauncher.requestPermission

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {
    override val presenter: SettingContract.Presenter = SettingPresenter(this)
    private lateinit var permissionResultLauncher: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initFragment()
    }

    override fun makeSettingSwitch() {
        val view = view ?: return
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        val permission = Manifest.permission.POST_NOTIFICATIONS
        permissionResultLauncher = makePermissionResultLauncher {
            onNotificationAddResult(switch, it)
        }

        switch.isChecked =
            SharedSetting.getValue(SETTING_NOTIFICATION) && PermissionLauncher.isGranted(
            requireContext(), permission
        )

        switch.setOnCheckedChangeListener { _, isChecked ->
            presenter.toggleNotificationSetting(
                permission, isChecked
            )
        }
    }

    private fun onNotificationAddResult(
        switch: SwitchCompat,
        isGranted: Boolean
    ) {
        switch.isChecked = isGranted
        SharedSetting.setValue(SETTING_NOTIFICATION, isGranted)
    }

    override fun onNotificationSwitchCheckedChangeListener(
        permission: String,
        isChecked: Boolean
    ) {
        if (isChecked) {
            if (PermissionLauncher.isGranted(requireContext(), permission)) SharedSetting.setValue(
                SETTING_NOTIFICATION, true
            )
            else requestPermission(permission, permissionResultLauncher)
            return
        }
        SharedSetting.setValue(SETTING_NOTIFICATION, false)
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
