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
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.ReservationModel
import woowacourse.movie.ui.alarm.ReservationAlarmManager
import woowacourse.movie.utils.getPreferences

class SettingFragment : Fragment() {
    private lateinit var toggleButton: SwitchCompat
    private val reservationAlarmManager by lazy { ReservationAlarmManager(requireContext()) }
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
            if (ContextCompat.checkSelfPermission(
                    view.context,
                    android.Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
                return
            }

            if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // 권한 요청 거부한 경우
                return
            }

            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun initToggleButton(view: View) {
        toggleButton = view.findViewById(R.id.setting_switch)
        toggleButton.isChecked = requireContext().getPreferences().getBoolean(KEY_SWITCH, false)
    }

    private fun setClickEventOnToggleButton() {
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            requireContext().getPreferences().edit { putBoolean(KEY_SWITCH, isChecked) }
            setAlarms(isChecked)
        }
    }

    private fun setAlarms(isChecked: Boolean) {
        if (isChecked) {
            iterateOnTickets(reservationAlarmManager::makeAlarm)
        } else {
            iterateOnTickets(reservationAlarmManager::cancelAlarm)
        }
    }

    private fun iterateOnTickets(event: (MovieTicketModel) -> Unit) {
        ReservationModel.tickets.forEach { ticket ->
            event(ticket)
        }
    }

    companion object {
        const val KEY_SWITCH = "switch"
    }
}
