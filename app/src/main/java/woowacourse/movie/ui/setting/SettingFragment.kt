package woowacourse.movie.ui.setting

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.model.data.AlarmSwitchStateImpl
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.ReservationTicketMachine
import woowacourse.movie.ui.alarm.Alarm

class SettingFragment : Fragment() {
    private lateinit var toggleButton: SwitchCompat
    private val alarmSwitchStateImpl: AlarmSwitchStateImpl by lazy {
        AlarmSwitchStateImpl.getInstance(
            requireContext(),
        )
    }
    private val alarm by lazy { Alarm(requireContext()) }
    private val requestPermissionLauncher by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        requestNotificationPermission(view)
        initToggleButton(view)
        setClickEventOnToggleButton()
        return view
    }

    private fun requestNotificationPermission(view: View) {
        if (ContextCompat.checkSelfPermission(
                view.context,
                android.Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    // 권한 요청 거부한 경우
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }

    private fun initToggleButton(view: View) {
        toggleButton = view.findViewById(R.id.setting_switch)
        toggleButton.isChecked = alarmSwitchStateImpl.isAlarmActivated
    }

    private fun setClickEventOnToggleButton() {
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            alarmSwitchStateImpl.isAlarmActivated = isChecked
            setAlarms(isChecked)
        }
    }

    private fun setAlarms(isChecked: Boolean) {
        if (isChecked) {
            iterateOnTickets(alarm::makeAlarm)
        } else {
            iterateOnTickets(alarm::cancelAlarm)
        }
    }

    private fun iterateOnTickets(event: (MovieTicketModel) -> Unit) {
        ReservationTicketMachine.tickets.forEach { ticket ->
            event(ticket)
        }
    }

    companion object {
        const val KEY_MOVIE = "movie"
    }
}
