package woowacourse.movie.view.moviemain.setting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.AlarmPreference
import woowacourse.movie.R
import woowacourse.movie.data.ReservationMockRepository
import woowacourse.movie.util.isGranted
import woowacourse.movie.util.requestRequiredPermissions
import woowacourse.movie.view.AlarmController
import woowacourse.movie.view.mapper.toUiModel

class SettingFragment : Fragment() {

    private lateinit var alarmController: AlarmController
    private lateinit var alarmPreference: AlarmPreference
    private lateinit var toggle: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alarmPreference = AlarmPreference(requireContext())
        alarmController = AlarmController(requireContext())
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

        toggle = view.findViewById(R.id.setting_toggle)
        val isAlarmOn = alarmPreference.getBoolean(IS_ALARM_ON, false)

        initToggle(toggle, isAlarmOn)
    }

    private fun initToggle(toggle: SwitchMaterial, isAlarmOn: Boolean) {
        toggle.isChecked = isAlarmOn
        toggle.setOnCheckedChangeListener { _, isChecked ->
            setToggleChangeListener(isChecked)
        }
    }

    private fun setToggleChangeListener(isChecked: Boolean) {
        if (isChecked && requestNotificationPermission()) {
            resetAlarms()
            alarmPreference.putBoolean(IS_ALARM_ON, true)
        } else {
            alarmController.cancelAlarms()
            alarmPreference.putBoolean(IS_ALARM_ON, false)
        }
    }

    private fun resetAlarms() {
        val reservations = ReservationMockRepository.findAll().map { it.toUiModel() }
        alarmController.registerAlarms(reservations, ALARM_MINUTE_INTERVAL)
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            toggle.isChecked = true
            alarmPreference.putBoolean(IS_ALARM_ON, true)
        } else {
            Toast.makeText(
                requireContext(),
                "권한을 설정해야 알림을 받을 수 있습니다. 설정에서 알림을 켜주세요.",
                Toast.LENGTH_LONG
            ).show()
            toggle.isChecked = false
            alarmPreference.putBoolean(IS_ALARM_ON, false)
        }
    }

    companion object {
        const val IS_ALARM_ON = "IS_ALARM_ON"
        const val ALARM_MINUTE_INTERVAL = 30L
    }
}
