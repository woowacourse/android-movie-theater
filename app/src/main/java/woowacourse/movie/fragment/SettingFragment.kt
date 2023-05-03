package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.domain.setting.Setting
import woowacourse.movie.service.PermissionManager
import woowacourse.movie.service.PermissionManager.checkNotificationSelfPermission
import woowacourse.movie.service.PermissionManager.requestNotificationPermission
import woowacourse.movie.view.setting.SharedSetting

class SettingFragment : Fragment() {

    private val requestPermissionLauncher =
        PermissionManager.getRequestPermissionLauncher(this, ::onPermissionGranted)

    private val switch: SwitchCompat by lazy {
        requireView().findViewById(R.id.setting_push_switch)
    }

    private val setting: Setting by lazy {
        SharedSetting(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitch()
    }

    private fun initSwitch() {
        initSwitchCheckStatus()
        switch.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(isChecked)
        }
    }

    private fun initSwitchCheckStatus() {
        if (!requireContext().checkNotificationSelfPermission()) {
            switch.isChecked = false
        } else {
            switch.isChecked = setting.getValue(SETTING_NOTIFICATION)
        }
    }

    private fun onCheckedChange(isChecked: Boolean) {
        setting.setValue(SETTING_NOTIFICATION, isChecked)
        if (isChecked && !requireContext().checkNotificationSelfPermission()) {
            requestNotificationPermission(this, requestPermissionLauncher, ::requestPermission)
        }
    }

    private fun onPermissionGranted() {
        switch.isChecked = false
    }

    private fun requestPermission() {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.permission_instruction_ment),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
