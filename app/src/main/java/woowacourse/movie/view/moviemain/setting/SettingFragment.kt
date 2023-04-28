package woowacourse.movie.view.moviemain.setting

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.R
import woowacourse.movie.data.ReservationMockRepository
import woowacourse.movie.view.AlarmController
import woowacourse.movie.view.mapper.toUiModel

class SettingFragment : Fragment() {

    private lateinit var alarmController: AlarmController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var toggle: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
            requireContext().getSharedPreferences(ALARM_SETTING, Context.MODE_PRIVATE)
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
        val isAlarmOn = sharedPreferences.getBoolean(IS_ALARM_ON, false)

        initToggle(toggle, isAlarmOn)
    }

    private fun initToggle(toggle: SwitchMaterial, isAlarmOn: Boolean) {
        toggle.isChecked = isAlarmOn
        toggle.setOnCheckedChangeListener { _, isChecked ->
            setToggleChangeListener(isChecked)
        }
    }

    private fun setToggleChangeListener(isChecked: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        if (isChecked && requestNotificationPermission()) {
            resetAlarms()
            editor.putBoolean(IS_ALARM_ON, true).apply()
        } else {
            alarmController.cancelAlarms()
            editor.putBoolean(IS_ALARM_ON, false).apply()
        }
    }

    private fun resetAlarms() {
        val reservations = ReservationMockRepository.findAll().map { it.toUiModel() }
        alarmController.registerAlarms(reservations, ALARM_MINUTE_INTERVAL)
    }

    private fun requestNotificationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(
                requireContext(),
                "권한을 설정해야 알림을 받을 수 있습니다. 설정에서 알림을 켜주세요.",
                Toast.LENGTH_LONG
            ).show()
            toggle.isChecked = false
        }
    }

    companion object {
        const val ALARM_SETTING = "ALARM_SETTING"
        const val IS_ALARM_ON = "IS_ALARM_ON"
        const val ALARM_MINUTE_INTERVAL = 30L
    }
}
