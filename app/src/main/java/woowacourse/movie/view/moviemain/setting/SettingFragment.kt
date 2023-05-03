package woowacourse.movie.view.moviemain.setting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.AlarmPreference
import woowacourse.movie.R
import woowacourse.movie.data.ReservationMockRepository
import woowacourse.movie.util.isGranted
import woowacourse.movie.util.requestRequiredPermissions
import woowacourse.movie.view.alarm.ReservationAlarmManager
import woowacourse.movie.view.mapper.toUiModel

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var reservationAlarmManager: ReservationAlarmManager
    private lateinit var alarmPreference: AlarmPreference
    private lateinit var toggle: SwitchMaterial
    private var initialToggleValue: Boolean = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            toggle.isChecked = true
            alarmPreference.setIsAlarmOn(true)
        } else {
            Toast.makeText(
                requireContext(),
                "권한을 설정해야 알림을 받을 수 있습니다. 설정에서 알림을 켜주세요.",
                Toast.LENGTH_LONG
            ).show()
            toggle.isChecked = false
            alarmPreference.setIsAlarmOn(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alarmPreference = AlarmPreference.getInstance(requireActivity().applicationContext)
        reservationAlarmManager = ReservationAlarmManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggle = view.findViewById(R.id.setting_toggle)
        val isAlarmOn = alarmPreference.isAlarmOn(false)

        initToggle(toggle, isAlarmOn)
    }

    private fun initToggle(toggle: SwitchMaterial, isAlarmOn: Boolean) {
        toggle.isChecked = isAlarmOn
        initialToggleValue = isAlarmOn
        toggle.setOnCheckedChangeListener { _, isChecked ->
            setToggleChangeListener(isChecked)
        }
    }

    private fun setToggleChangeListener(isChecked: Boolean) {
        if (isChecked) requestNotificationPermission()
    }

    private fun requestNotificationPermission(): Boolean {
        val permissionsRequired = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permissionsRequired.add(Manifest.permission.POST_NOTIFICATIONS)
        requireActivity().requestRequiredPermissions(
            permissionsRequired,
            requestPermissionLauncher::launch
        )
        return requireActivity().isGranted(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun onStop() {
        super.onStop()
        val isToggleChecked = toggle.isChecked
        if (initialToggleValue != isToggleChecked) {
            applyChange(isToggleChecked)
        }
    }

    private fun applyChange(isToggleChecked: Boolean) {
        if (isToggleChecked) {
            resetAlarms()
            alarmPreference.setIsAlarmOn(true)
        } else {
            reservationAlarmManager.cancelAlarms()
            alarmPreference.setIsAlarmOn(false)
        }
    }

    private fun resetAlarms() {
        val reservations = ReservationMockRepository.findAll().map { it.toUiModel() }
        reservationAlarmManager.registerAlarms(reservations, ALARM_MINUTE_INTERVAL)
    }

    companion object {
        const val ALARM_MINUTE_INTERVAL = 30L
    }
}
