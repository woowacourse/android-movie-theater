package woowacourse.app.presentation.ui.main.setting

import android.Manifest
import android.app.NotificationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import woowacourse.app.data.pushAlarm.SettingSharedPreference
import woowacourse.app.presentation.ui.seat.NotiChannel
import woowacourse.app.presentation.util.createChannel
import woowacourse.app.presentation.util.requestNotificationPermission
import woowacourse.movie.R

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter

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
        presenter = SettingPresenter(this, SettingSharedPreference(view.context))
    }

    override fun setSwitch(isPushAlarmOn: Boolean) {
        switch.isChecked = isPushAlarmOn
        setSwitchCheckedChange()
    }

    private fun setSwitchCheckedChange() {
        switch.setOnCheckedChangeListener { switch, isChecked ->
            if (isChecked) {
                switch.context.requestNotificationPermission {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            presenter.setPushAlarmSetting(isChecked)
        }
    }
}
