package woowacourse.movie.view.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.contract.SettingContract
import woowacourse.movie.data.dataSource.SharedSetting
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presenter.SettingPresenter
import woowacourse.movie.system.isGranted
import woowacourse.movie.system.makePermissionResultLauncher
import woowacourse.movie.system.requestPermission

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter
    private lateinit var permissionResultLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SettingPresenter(this)
    }

    override fun makeSettingSwitch() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        permissionResultLauncher = makePermissionResultLauncher {
            onNotificationAddResult(binding.settingPushSwitch, it)
        }

        binding.settingPushSwitch.isChecked =
            SharedSetting.getValue(SETTING_NOTIFICATION) && isGranted(
            requireContext(), permission
        )

        binding.settingPushSwitch.setOnCheckedChangeListener { _, isChecked ->
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
            if (isGranted(requireContext(), permission)) SharedSetting.setValue(
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
