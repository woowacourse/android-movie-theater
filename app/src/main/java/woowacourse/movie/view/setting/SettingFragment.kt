package woowacourse.movie.view.setting

import android.Manifest
import android.app.AlertDialog
import android.content.Context
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
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmBtn: SwitchCompat

    private val sharedPref by lazy {
        requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->

            alarmBtn.isChecked = isGranted
            sharedPref.edit { putBoolean(KEY_IS_ALARM_ON, isGranted) }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        val isAlarmOn = sharedPref.getBoolean(KEY_IS_ALARM_ON, false)
        setAlarmBtn(isAlarmOn)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAlarmBtn(isAlarmOn: Boolean) {
        alarmBtn = binding.settingAlarmSwitchBtn
        alarmBtn.isChecked = isAlarmOn

        alarmBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestNotificationPermission()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    sharedPref.edit { putBoolean(KEY_IS_ALARM_ON, true) }
                }
                return@setOnCheckedChangeListener
            }

            alarmBtn.isChecked = false
            sharedPref.edit { putBoolean(KEY_IS_ALARM_ON, false) }
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    showPermissionDialog()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }

    private fun showPermissionDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("@string/setting_notification_title")
        builder.setMessage("@string/setting_notification_description")
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.show()
    }

    companion object {
        private const val PREFS_NAME = "setting"
        private const val KEY_IS_ALARM_ON = "isAlarmOn"
    }
}
