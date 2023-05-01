package woowacourse.movie.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.permission.getPermissionLauncher
import woowacourse.movie.permission.requestPermission
import woowacourse.movie.util.SettingSharedPreference

class SettingFragment : Fragment() {

    private val settingSharedPreference by lazy {
        SettingSharedPreference(requireContext())
    }
    private val switch: Switch by lazy {
        requireActivity().findViewById(R.id.switchPushAlarm)
    }
    private val permissionLauncher: ActivityResultLauncher<String> =
        getPermissionLauncher(
            deniedCase = {
                switch.isChecked = false
                settingSharedPreference.receivingPushAlarm = false
            },
            allowedCase = {
                switch.isChecked = true
                settingSharedPreference.receivingPushAlarm = true
            }
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSwitch()
    }

    private fun initSwitch() {
        switch.isChecked = settingSharedPreference.receivingPushAlarm
        setCheckedChangedListener(switch)
    }

    private fun setCheckedChangedListener(switch: Switch) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestPermission(
                    switch.context,
                    permissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
                settingSharedPreference.receivingPushAlarm = true
            } else {
                settingSharedPreference.receivingPushAlarm = false
            }
        }
    }
}
