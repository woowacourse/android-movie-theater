package woowacourse.movie.view.moviemain.setting

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.R
import woowacourse.movie.data.ReservationMockRepository
import woowacourse.movie.view.AlarmController
import woowacourse.movie.view.mapper.toUiModel

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var alarmController: AlarmController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var toggle: SwitchMaterial

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(requireContext(), "권한을 설정해야 알림을 받을 수 있습니다. 설정에서 알림을 켜주세요.", Toast.LENGTH_LONG).show()
            toggle.isChecked = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        alarmController = AlarmController(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggle = view.findViewById(R.id.setting_toggle)

        val savedToggle = sharedPreferences.getBoolean(IS_ALARM_ON, false)
        toggle.isChecked = savedToggle

        toggle.setOnCheckedChangeListener { _, isChecked ->
            setToggleChangeListener(isChecked)
        }
    }

    private fun setToggleChangeListener(isChecked: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        if (isChecked && requestNotificationPermission()) {
            val reservations = ReservationMockRepository.findAll().map { it.toUiModel() }
            alarmController.registerAlarms(reservations, ALARM_MINUTE_INTERVAL)
            editor.putBoolean(IS_ALARM_ON, true).apply()
            return
        }
        alarmController.cancelAlarms()
        editor.putBoolean(IS_ALARM_ON, false).apply()
    }

    private fun requestNotificationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true // android 12 version 이하는 notification 권한 필요 없기 때문
    }

    companion object {
        const val IS_ALARM_ON = "IS_ALARM_ON"
        const val ALARM_MINUTE_INTERVAL = 30L
    }
}
