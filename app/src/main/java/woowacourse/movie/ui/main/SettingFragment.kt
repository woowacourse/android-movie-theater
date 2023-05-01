package woowacourse.movie.ui.main

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.ui.seat.NotiChannel
import woowacourse.movie.util.SettingSharedPreference
import woowacourse.movie.util.createChannel
import woowacourse.movie.util.requestNotificationPermission

class SettingFragment : Fragment() {
    private val switch: Switch by lazy {
        requireActivity().findViewById(R.id.switchPushAlarm)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            switch.isChecked = isGranted
            if (isGranted) {
                createChannel(
                    context = requireContext(),
                    channel = NotiChannel.BOOKING_ALARM,
                    importance = NotificationManager.IMPORTANCE_HIGH,
                )
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingSharePreference = SettingSharedPreference(view.context)
        setSwitch(settingSharePreference)
    }

    private fun setSwitch(settingSharePreference: SettingSharedPreference) {
        switch.isChecked = settingSharePreference.receivingPushAlarm
        setSwitchCheckedChange(switch.context, settingSharePreference)
    }

    private fun setSwitchCheckedChange(
        context: Context,
        settingSharePreference: SettingSharedPreference,
    ) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestNotificationPermission(context) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            settingSharePreference.receivingPushAlarm = isChecked
        }
    }
}
