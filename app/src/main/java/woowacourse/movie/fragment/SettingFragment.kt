package woowacourse.movie.fragment

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY
import woowacourse.movie.PermissionManager
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.activity.MainActivity

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
        val canPushSwitch = view.findViewById<SwitchMaterial>(R.id.sw_setting_can_push)

        synchronizeCanPushSwitch(canPushSwitch)
        setCanPushSwitchOnClickListener(canPushSwitch)
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
            if (PermissionManager.isPermissionDenied(requireContext(), POST_NOTIFICATIONS)) {
                if (!shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                    switch.isEnabled = false
                }
                if (isChecked) (activity as MainActivity).requestPermissionLauncher.launch(
                    POST_NOTIFICATIONS
                )
            }
        }
    }
}
