package woowacourse.movie.fragment

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.BundleKeys.IS_CAN_PUSH_CHECKED
import woowacourse.movie.BundleKeys.REQUEST_NOTIFICATION_PERMISSION
import woowacourse.movie.BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY
import woowacourse.movie.PermissionManager
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val canPushSwitchView = view.findViewById<SwitchMaterial>(R.id.sw_setting_can_push)

        synchronizeCanPushSwitch(canPushSwitchView)
        setCanPushSwitchOnClickListener(canPushSwitchView)
    }

    private fun synchronizeCanPushSwitch(switch: SwitchMaterial) {
        switch.isChecked =
            SharedPreferenceUtil.getBooleanValue(
                requireContext(),
                SETTING_PUSH_ALARM_SWITCH_KEY,
                false
            )
    }

    private fun setCanPushSwitchOnClickListener(switch: SwitchMaterial) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            SharedPreferenceUtil.setBooleanValue(
                requireContext(),
                SETTING_PUSH_ALARM_SWITCH_KEY,
                isChecked
            )
            if (PermissionManager.isPermissionDenied(requireContext(), POST_NOTIFICATIONS)) {
                if (!shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                    switch.isEnabled = false
                }
                if (isChecked) {
                    setFragmentResult(
                        REQUEST_NOTIFICATION_PERMISSION,
                        bundleOf(IS_CAN_PUSH_CHECKED to isChecked)
                    )
                }
            }
        }
    }
}
